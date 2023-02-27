# Задание 3.  Шаблон проектирования "MVC", графический интерфейс пользователя (GUI).

## Постановка задачи

Написать аналог игры `Сапер` (`Minesweeper`) из состава стандартных программ для Windows OS;

## Требования к программе


1. Архитектура программы должна быть основана на паттерне `MVC (Mode-View-Controller)`;
2. Программа должна иметь два интерфейса – текстовый и графический,
причем оба интерфейса должны использовать одну и ту же игровую модель
(классы данных и логики должны быть одинаковые для текстового и графического интерфейсов);
3. Размер поля и количество мин можно изменить. По умолчанию поле размером $9x9$ и
   количество мин - 10;
4. Игра должна поддерживать таблицу рекордов.
5. Пользователю должны быть доступны команды: 
   + `Exit`;
   + `About`;
   + `New Game`;
   + `High Scores`;
6. Отчет времени должен быть реализован отдельным потоком;

## Пример структуры программы

```
/ru/nsu/ccfit/ФАМИЛИЯ/minesweeper – основные классы программы;
/ru/nsu/ccfit/ФАМИЛИЯ/minesweeper/text – классы текстового интерфейса пользователя;
/ru/nsu/ccfit/ФАМИЛИЯ/minesweeper/gui – классы графического интерфейса;
/ru/nsu/ccfit/ФАМИЛИЯ/minesweeper/resources – картинки и другие ресурсы;
```

## Реализация текстового UI

1. Команды пользователя вводятся с консоли, ячейки нумеруются от нуля;
2. После каждого хода игрока все игровое поле распечатывается на экран целиком;
   
## Реализация графического UI

1. Мины и флажки отображать с помощью картинок;
2. При формировании окна игры использовать класс `LayoutManager`. 
Для расположения элементов на игровой панели рекомендуется использовать класс `GridBagLayout`. 
Для расположения ячеек поля рекомендуется использовать класс `GridLayout`;

## Методические указания:

1. Шаблон проектирования `MVC`:
   + [RSDN](http://rsdn.ru/article/patterns/generic-mvc.xml);
   + [Wikipedia](http://ru.wikipedia.org/wiki/Model-View-Controller);
2. Для реализации пользовательского интерфейса использовать библиотеку `Swing` (классы из пакета `javax.swing.*`).
3. Работа с компонентами пользовательского интерфейса (классами библиотеки `Swing`) должна проходить только из UI потока.
4. Для отображения диалоговых окон рекомендуется использовать класс `JOptionPane`.