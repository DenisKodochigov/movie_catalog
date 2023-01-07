package com.example.movie_catalog

class ToDo {
}

/*
2. Проверки на доступность.

     2.9 Оптимизировать загрузку картинок. Для FilmInfo, только необходимое количество,
        для галереи все скачать.
3. Недочеты
    3.1





Раздельная линия в recyclerView
recyclerview.addItemDecoration(DividerItemDecoration(this@YourActivity, LinearLayoutManager.VERTICAL))
или
val divider = DividerItemDecoration(this@MainActivity,DividerItemDecoration.VERTICAL)
divider.setDrawable(ContextCompat.getDrawable(this@MainActivity,R.drawable.item_separator)!!)
recyclerview.addItemDecoration(divider)



Добавление метода getTonight():

В завершении будет добавлен метод для получения последней добавленной записи getTonight(). Метод также будет помечен аннотацией @Query с описанием запроса. Запрос похож на тот, что описан в методе по получению всех записей. Отличие в том, что здесь используется параметр LIMIT 1 для гарантированного получения лишь одной записи, а не списка.

@Query("SELECT * FROM sleep_quality_table ORDER BY id DESC LIMIT 1")
fun getTonight(): SleepNight?

 */