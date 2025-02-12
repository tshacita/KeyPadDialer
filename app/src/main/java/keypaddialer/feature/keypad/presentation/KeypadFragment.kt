package keypaddialer.feature.keypad.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.GONE
import android.view.ViewGroup.LayoutParams
import androidx.core.widget.doAfterTextChanged
import co.th.touchtechnologies.keypaddialer.databinding.FragmentKeyPadBinding
import keypaddialer.utils.KeyboardUtils
import keypaddialer.feature.keypad.presentation.adapter.KeypadGridAdapter
import keypaddialer.utils.ResponsiveUtils
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
        KeyboardUtils.hideSoftKeyboard(requireActivity())

        binding.gridKeypad.apply {
            if (ResponsiveUtils.isTablet(requireContext())) {
                setPadding(64, 16, 64, 16)
            } else {
                setPadding(12, 12, 12 ,12)
            }
        }

        binding.edtNo.apply {
            setSelection(binding.edtNo.length())
            if (ResponsiveUtils.isTablet(requireContext())) {
                textSize = 48f
            }
        }

        binding.btnDelete.apply {
            if (ResponsiveUtils.isTablet(requireContext())) {
                width = 80
                height = 80
                setPadding(40, 0, 0, 0)
                val params = layoutParams as ViewGroup.MarginLayoutParams
                params.setMargins(0, 0, 120, 0)
                layoutParams = params
                visibility = GONE
            }
        }

        binding.edtNo.apply {
            val params = layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(120, 0, 60, 0)
            layoutParams = params
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun listener() {

        binding.edtNo.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                binding.edtNo.isCursorVisible = true
                binding.edtNo.clearFocus()
                KeyboardUtils.hideSoftKeyboard(requireActivity())
            }
            v.performClick()
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