package com.example.movie_catalog.ui.film_info.share_dialog

//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.movie_catalog.databinding.ItemFilmInfoPersonBinding
//
//class SheetRecyclerAdapter(private val onClick: (item: DialogItemEntity) -> Unit
//) : RecyclerView.Adapter<SheetViewHolder>() {
//    private val dataSet = mutableListOf<DialogItemEntity>()
//
//    fun addItems(items: List<DialogItemEntity>) {
//        dataSet.addAll(items)
//        notifyDataSetChanged()
//    }
//
//    class ViewHolder(private val binding: RecyclerViewCellBottomSheetShareDialogBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(data: DialogItemEntity, onClickCallback: (itemEntity: DialogItemEntity) -> Unit) {
//            binding.tvTitleItemSheet.text = data.name
//            binding.imgItemSheet.background = data.drawable
//            binding.root.setOnClickListener {
//                onClickCallback.invoke(data)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
//        return SheetViewHolder(RecyclerViewCellBottomSheetShareDialogBinding.inflate(
//            LayoutInflater.from(viewGroup.context), viewGroup, false))
//    }
//
//    override fun getItemCount() = dataSet.size
//
//    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        val item = dataSet[position]
//        viewHolder.bind(item) {
//            onClick.invoke(item)
//        }
//    }
//}
//class SheetViewHolder(val binding: ItemFilmInfoPersonBinding): RecyclerView.ViewHolder(binding.root)