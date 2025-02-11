package keypaddialer.feature.keypad.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.BaseAdapter
import co.th.touchtechnologies.keypaddialer.databinding.ItemKeypadBinding

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

        if (isTablet(context = context)) {
            binding.btn.layoutParams = LayoutParams(240, 240)
        } else {
            binding.btn.layoutParams = LayoutParams(180, 180)
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

    private fun isTablet(context: Context): Boolean {
        val xlarge =
            ((context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) == 4)
        val large =
            ((context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE)
        return (xlarge || large)
    }
}