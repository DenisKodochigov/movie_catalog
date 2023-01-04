package com.example.movie_catalog

class ToDo {
}

/*
1. В галерее проставить имя в toolbar
2. Проверки на доступность.
    2.1 В viewmodel присваивать значения из take если они не равны null
    2.2 В DataRepository обращаться к API если нет данных.
    2.3 При добавлении новых данных все объекты копировать и проверять на наличие/
    2.4 Все функции сделать в таком стиле.
            private fun takeJobPerson() {
                val item = dataRepository.takeJobPerson()
                if (item != null) localJob = item
            }
    2. Показывать ID фильма

     2.9 Оптимизировать загрузку картинок. Для FilmInfo, только необходимое количество,
        для галереи все скачать.
3. Недочеты
    3.4 Нет анимации при открытии Person
    3.5 Карточка актера неимеет скролинга. Не видна фильмография.
    3.6 Проверить и передалать передачу значений для paginAdapter.



Раздельная линия в recyclerView
recyclerview.addItemDecoration(DividerItemDecoration(this@YourActivity, LinearLayoutManager.VERTICAL))
или
val divider = DividerItemDecoration(this@MainActivity,DividerItemDecoration.VERTICAL)
divider.setDrawable(ContextCompat.getDrawable(this@MainActivity,R.drawable.item_separator)!!)
recyclerview.addItemDecoration(divider)

 */

/*
  <LinearLayout
            android:id="@+id/pole_sorting"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:paddingBottom="@dimen/settings_padding_bottom"
            android:visibility="gone"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@id/tv_sorting">

            <TextView
                android:id="@+id/tv_data"
                style="@style/settings_search_group_start"
                android:background="@drawable/round_rect_shape_start"
                android:hint="@string/date" />

            <TextView
                android:id="@+id/tv_popular"
                style="@style/settings_search_group_start"
                android:background="@drawable/round_rect_shape_center"
                android:hint="@string/popularis" />

            <TextView
                android:id="@+id/tv_rating_sort"
                style="@style/settings_search_group_start"
                android:background="@drawable/round_rect_shape_end"
                android:hint="@string/rating" />
        </LinearLayout>
 */