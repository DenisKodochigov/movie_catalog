package com.example.movie_catalog

class ToDo {
}

/*
1. Сделать

2. Проверки на доступность.

3. Недочеты
    3.1 I/ViewTarget: Glide treats LayoutParams.WRAP_CONTENT as a request for an image the size
     of this device's screen dimensions. If you want to load the original image and are ok with
     the corresponding memory cost and OOMs (depending on the input size), use override
     (Target.SIZE_ORIGINAL). Otherwise, use LayoutParams.MATCH_PARENT, set layout_width and
     layout_height to fixed dimension, or use .override() with fixed dimensions.

    3.3 Объеденить фрагменты List_film и  kit_film




Может пригодиться.

Раздельная линия в recyclerView
recyclerview.addItemDecoration(DividerItemDecoration(this@YourActivity, LinearLayoutManager.VERTICAL))
или
val divider = DividerItemDecoration(this@MainActivity,DividerItemDecoration.VERTICAL)
divider.setDrawable(ContextCompat.getDrawable(this@MainActivity,R.drawable.item_separator)!!)
recyclerview.addItemDecoration(divider)


Метод для получения последней добавленной записи getTonight().
Метод также будет помечен аннотацией @Query с описанием запроса. Запрос похож на тот,
что описан в методе по получению всех записей. Отличие в том, что здесь используется
параметр LIMIT 1 для гарантированного получения лишь одной записи, а не списка.

@Query("SELECT * FROM sleep_quality_table ORDER BY id DESC LIMIT 1")
fun getTonight(): SleepNight?

 */