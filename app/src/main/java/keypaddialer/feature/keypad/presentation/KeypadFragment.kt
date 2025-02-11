package keypaddialer.feature.keypad.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import co.th.touchtechnologies.keypaddialer.databinding.FragmentKeyPadBinding
import keypaddialer.KeyboardUtils
import keypaddialer.feature.keypad.presentation.adapter.KeypadGridAdapter
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class KeypadFragment : Fragment() {
    private lateinit var binding: FragmentKeyPadBinding
    private val vm: KeypadViewModel by activityViewModel()
    private var keypadAdapter: KeypadGridAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKeyPadBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        listener()
        observation()
    }

    private fun setup() {
        binding.edtNo.setSelection(binding.edtNo.length())
        binding.btnDelete.visibility = View.GONE
        KeyboardUtils.hideSoftKeyboard(requireActivity())
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun listener() {

        binding.edtNo.setOnTouchListener { v, event ->
            v.onTouchEvent(event)
            binding.edtNo.isCursorVisible = false
            KeyboardUtils.hideSoftKeyboard(requireActivity())
            true
        }

        binding.edtNo.doAfterTextChanged {
            vm.inputField(text = it.toString())
        }

        binding.btnDelete.setOnClickListener {
            KeyboardUtils.hideSoftKeyboard(activity = requireActivity())
            if (binding.edtNo.selectionStart == binding.edtNo.selectionEnd) {
                vm.deleteLastDigit()
            } else {
                vm.deleteCursorPosition(
                    IntRange(
                        start = binding.edtNo.selectionStart,
                        endInclusive = binding.edtNo.selectionEnd - 1
                    )
                )
            }
        }
    }

    private fun observation() {
        vm.numPad.observe(viewLifecycleOwner) { items ->
            items?.let {
                keypadAdapter = KeypadGridAdapter(
                    context = requireContext(),
                    items = vm.numPad.value ?: arrayListOf(),
                    onItemClickListener = object : KeypadGridAdapter.OnItemClickListener {
                        @SuppressLint("SetTextI18n")
                        override fun onItemClick(item: String?) {
                            binding.edtNo.setText(binding.edtNo.text.toString() + item)
                        }
                    }
                )

                binding.gridKeypad.apply {
                    adapter = keypadAdapter
                }
            }
        }

        vm.item.observe(viewLifecycleOwner) { items ->
            binding.edtNo.setText(items)
            binding.edtNo.setSelection(binding.edtNo.length())
            binding.edtNo.isCursorVisible = false
            if (items.isNullOrEmpty()) {
                binding.btnDelete.visibility = View.GONE
            } else {
                binding.btnDelete.visibility = View.VISIBLE
            }

            if (!vm.validates(text = items)) {
                vm.deleteLastDigit()
            }
        }
    }
}