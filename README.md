# ResultatTavalaKanot
Ett program för att kunna läsa in resultat som skapas under tävlings gång och puscha dem till en server för att kunna visas upp på nätet live. Samt ändra och fixa reslutaten om behov finns.

1. Så det tilltänka området för programet är att man väljer en mapp som programet hela tiden läser igenom och letar efter nya filer. 
2. När ny fil av rätt typ hittas nu en PDF resultatfil hittas, parsars den och resultatten läses in
3. Resultaten går att se och modifieras om parsningen gick dåligt (PDF:er är inte så bra att läsa in...)
4. Sedan när man känner för det puscha det till en server (se annat prodjekt) som tar emot och skickar det vidare med ett rest API samt tillhandahåller en hemsida som visar upp reslutaten.
5. Så man kan gå in på nätet och i i stort realtid se resultaten


Funkar rikigt fint faktiskt, men en del bör kunna förbättras / fixas /läggas till. Sedan är inte koden den finaste heller då det blev rätt ont om tid att skriva klart alla innan tävlingen som jagtänkt tästa/ha det till.

Men några saker jag vet och tänkte fixa när jag får tid
- fixa en mindre UI defekt om att fönstret är lite för litet vid start
- inläsningen göra den stabilare eller helst se om man kan få den att få in reslutaen på annat sätt typ genom databasen som tävlingsystemet använder (det är inte något jag skriver eller har hand om utan detta är helt separat)
- göra så att den också kan pusha resultaten med jämna tidintervaller
