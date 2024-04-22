# Коммутатор

![Строит мосты.](oredict:opencomputers:switch)

*Этот блок устарел и будет удален в следующих версиях.* Замените его на [ретранслятор](relay.md).

Коммутатор используется для передачи сообщений между несколькими подсетями, без предоставления компонентов [компьютерам](../general/computer.md) в других подсетях. Как правило, изоляция компонентов - хорошая идея, чтобы не позволить [компьютерам](../general/computer.md) использовать не тот [монитор](screen1.md) или избежать достижения лимита компонентов (в результате чего [компьютеры](../general/computer.md) выключатся и отказываются загружаться).

Также есть беспроводная версия коммутатора, называемая [точкой доступа](accessPoint.md). С ее помощью сообщения передаются по беспроводному каналу. Беспроводные сообщения могут быть получены или перенаправлены другими [точками доступа](accessPoint.md) или [компьютерами](../general/computer.md) с [беспроводной сетевой картой](../item/wlanCard1.md).

Коммутаторы и [точки доступа](accessPoint.md) *не* отслеживают, какие пакеты и куда они передали, поэтому для в сети могут образовываться петли или вы можете получать одно сообщение несколько раз. Из-за ограниченного буфера сообщений коммутатора пакеты могут теряться, если их отсылать слишком часто. Вы можете улучшить коммутатор или [точку доступа](accessPoint.md) для увеличения скорости обработки сообщений, а также увеличения размера буфера сообщений.

Сообщения могут перенаправлены всего несколько раз, поэтому невозможно сделать цепочки с произвольным количеством коммутаторов или точек доступа. По умолчанию сообщение может быть перенаправлено до пяти раз.