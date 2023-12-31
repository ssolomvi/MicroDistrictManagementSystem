# MicroDistrictManagementSystem
Курсовая работа по курсу «Базы данных». Тема: «Жилфонд микрорайона»
##  Анализ предметной области
Приложение предназначено для трех типов пользователей:
собственник, правление микрорайона и администратор жилищно-коммунального хозяйства. \
Каждый из этих типов пользователей имеет свои специфические потребности:
- собственник должен  иметь возможность учитывать свои объекты недвижимости, знать его управляющую компанию; 
- правление микрорайона должно иметь возможность добавлять, обновлять и удалять записи о факте владения собственником конкретного жилфонда, о недвижимости, домах, улицах, об удобствах, о жилищно-коммунальных хозяйств, искать объекты недвижимости по владельцу и наоборот – владельца по объекту недвижимости;
- администратор жилищно-коммунальных хозяйств должен иметь возможность знать обслуживаемые дома, своих клиентов – собственников объектов недвижимости и организаций в этом доме.
После анализа и описания предметной области приступим к постановке задачи. 
## Постановка задачи
Разработать прикладное программное обеспечение, объединяющее в себе 3 приложения для каждой из категорий пользователей: собственник, правление микрорайона, администратор жилищно-коммунального хозяйства. 
## Функциональность
Собственник:
- принадлежащие ему объекты недвижимости [в виде отчета];
- управляющие компании для каждого объекта недвижимости [в виде отчета].

Правление микрорайона:
- добавление, удаление, обновление записей об объектах недвижимости, собственниках, домах, улицах, парковках;
- поиск владельца по объекту недвижимости;
- поиск всех объектов недвижимости владельца [в виде отчета].

Администратор жилищно-коммунального хозяйства:
- список обслуживаемых домов [в виде отчета];
- клиенты этого жилищно-коммунального хозяйства [в виде отчета].
## Инструменты для создания приложения и проектирования базы данных
СУБД - postgreSQL \
IDE - DataGrip 2023.2.1, IntelliJ IDEA \
CASE - Erwin Data Modeler \
Интерфейс - SceneBuilder \
Генерация отчетов в формате .pdf - itext \
Сборка с Maven, SDK - Liberica-17
