**Готовые тесты** 
1. POST
- создать todo и проверить что создано

a) text
- создать todo с пустым значением в тексте (результат в зависимости от предпочтений заказчика и документации)
- создать todo без переменной текста
- создать todo с null значением

b) completed
- создать todo c completed false

2. PUT
- изменить todo и проверить что изменился, а остальные значения остались как были (решено с помощью containsOnlyOnce)
- проверить с несуществующим id числовым

a) text
- изменить todo с пустым значением в тексте (результат в зависимости от предпочтений заказчика и документации)
- изменить todo без переменной текста

b) completed
- создать todo c completed false

3. DELETE
- удалить todo и проверить что удалился, а остальные записи остались (решено с помощью containsOnlyOnce)
- проверить с уже удаленным id

4. GET получить список todo и проверить что отображаются корректно каждое поле


**В работе**
1. POST
a) text
- создать todo с максимальным количеством текста (результат в зависимости от предпочтений заказчика и документации)
- создать todo со знаками препинания
- создать todo только с цифрами
- проверка поддерживаемых языков (русский и пр., в зависимости от предпочтений заказчика и документации)

b) id
- создать todo с повторяющимся id
- создать todo без id
- создать todo c id = 0
- создать todo c id = -1
- создать todo c id = 1.1
- создать todo с неверным форматом в id (буква, если пишет что должен быть int - больше проверок не нужно)
- проверка максимального значения id (на случай если где-то внедрили short etc., максимальное значение 
согласовываем с заказчиком - int/long/bigint etc.)
- проверка значения id выше максимального

c) completed
- создать todo с completed true
- создать todo с неверным форматом в completed (цифра 1, 
если пишет что должен быть boolean - больше проверок не нужно)


2. PUT

а) text
- создать todo с максимальным количеством текста (результат в зависимости от предпочтений заказчика и документации)
- изменить todo со знаками препинания
- изменить todo только с цифрами
- проверка поддерживаемых языков (русский и пр., в зависимости от предпочтений заказчика и документации)

b) id
- изменить todo без id

c) completed
- изменить todo с completed true
- изменить todo с неверным форматом в completed

3. Дополнительная проверка каждого запроса:
- выполнить запрос во время проблемы подключения к БД
- выполнить запрос во время проблемы подключения к смежным сервисам (при наличии)
- выполнить запрос при нагрузке на компоненты системы (опционально)

4. Проверка ws должна быть объединена с каждым тестов из пунктов 1-4 (необходима донастройка restTemplate)