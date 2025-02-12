package keypaddialer.feature.keypad.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.BaseAdapter
import co.th.touchtechnologies.keypaddialer.databinding.ItemKeypadBinding
import keypaddialer.utils.ResponsiveUtils

class KeypadGridAdapter(
    private val context: Context,
    private val  items: List<String?>,
    val onItemClickListener: OnItemClickListener
): BaseAdapter() {
    private lateinit var binding: ItemKeypadBinding

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): String {
        return items[position].toString()
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        binding = ItemKeypadBinding.inflate(LayoutInflater.from(parent?.context), parent, false)

        if (ResponsiveUtils.isTablet(context = context)) {
            binding.btn.layoutParams = LayoutParams(300, 300)
            binding.btn.textSize = 48f
        } else {
            binding.btn.layoutParams = LayoutParams(220, 220)
        }
        binding.btn.setOnClickListener {
            onItemClickListener.onItemClick(item = items[position])
        }

        binding.btn.text = items[position].toString()
        return binding.root
    }

    interface OnItemClickListener {
        fun onItemClick(item: String?)
    }
}