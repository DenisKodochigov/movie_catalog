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
3. Не дочеты
    3.4 Нет анимации при открытии Person
 */