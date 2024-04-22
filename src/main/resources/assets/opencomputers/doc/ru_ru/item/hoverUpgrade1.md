# Улучшение "Парение"

![Легкое, как перышко.](oredict:opencomputers:hoverUpgrade1)

Это улучшение позволяет [роботам](../block/robot.md) летать гораздо выше над землёю, чем обычно. В отличие от [дронов](drone.md), максимальная высота их полета ограничена 8 блоками по умолчанию. Обычно это не доставляет больших проблем, потому что они все также могут двигаться по стенам и просто вверх. Правила их передвижения могут быть сведены к следующему:
- Робот будет двигаться при условии, что или стартовая, или конечная точки являются разрешёнными.
- Позиция под роботом всегда разрешана.
- Рзарешены точки на высоте до значения опции `flightHeight` в файле конфигурации от поверхности.
- Разрешена любая позиция, рядом с которым находится блок с непрозрачной стороной по направлению к ней, то есть роботы могут "карабкаться".

Эти правила, за исключением правила 2 (всегда может двигаться вниз), могут быть изображены так:
![Визуализация правил передвижения роботов.](opencomputers:doc/img/robotMovement.png)

Иными словами, если вы не хотите беспокоиться об ограничениях высоты полета, установите это улучшение.