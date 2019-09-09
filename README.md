# ResultatTavalaKanot
Ett program för att kunna läsa in resultat som skapas under tävlings gång och puscha dem till en server för att kunna visas upp på nätet live. Samt ändra och fixa reslutaten om behov finns.

1. Så det tilltänka området för programet är att man väljer en mapp som programet hela tiden läser igenom och letar efter nya filer. 
2. När ny fil av rätt typ hittas nu en PDF resultatfil hittas, parsars den och resultatten läses in
3. Resultaten går att se och modifieras om parsningen gick dåligt (PDF:er är inte så bra att läsa in...)
4. Sedan när man känner för det puscha det till en server (se annat prodjekt) som tar emot och skickar det vidare med ett rest API samt tillhandahåller en hemsida som visar upp reslutaten.
5. Så man kan gå in på nätet och i i stort realtid se resultaten


Funkar rikigt fint faktiskt, men en del bör kunna förbättras/fixas/läggas till. Sedan är inte koden den finaste heller då det blev rätt ont om tid att skriva klart alla innan tävlingen som jagtänkt tästa/ha det till.

Men några saker jag vet och tänkte fixa när jag får tid
- [x] Kuna läsa in direkt från databas
- [x] gör om till ett maven prodjekt (separata jarfiler känns lite si-sådär)
- [ ] Fixa en kalender vy som man kan visa när på dagen loppen går (är i stort klart men i separat prodjekt för själva vyn, bör snart komma upp på git) så att implementera den i prodjektet.
- [x] fixa en mindre UI defekt om att fönstret är lite för litet vid start
- [x] inläsningen göra den stabilare eller helst se om man kan få den att få in reslutaen på annat sätt typ genom databasen som tävlingsystemet använder (det är inte något jag skriver eller har hand om utan detta är helt separat)
- [x] göra så att den också kan pusha resultaten med jämna tidintervaller (automatiskt och inte behöver säga till den var gång)
- [ ] göra så att man kan lägga till nya banor direkt i programet.
- [ ] gärna lite interaktion så den kanvara lättare att lägga till personer, typ att den ger förslag på personer och klubbar
- [ ] Något som kan städa upp röra med PDF inläsing man får Parsningen nu typ att den tittar på olika typer och ser och försöker sätta alla som hamnar i fler kolumn i rätt kolumn. Sedan om den också skulle kunna matcha namn mot en lista så skulle den kunna utföra uppdelnigne mellan namn och klubb bättre när de täcker hela raden. Eller bara skriva om parsningen så den tittar på om råden istället för att bara plocka ut det som en stor klump.
- [ ] Arbeta på att den ska bli "snål" på "internet" den andvänder. (Bara puscha om den måste [x], komprimera innan den puchar [ ], baraq pu7scha diffar [ ])

##Testköra
Programet är designat för att läsa in resulat PDF:er eller direkt från tävlingsdatabasen som kommer ur kanottävlingssytem (ERTornamnet) producerar ([se dem här](https://github.com/flaime/ResultatTavalaKanot/tree/master/PDFer%20att%20testa%20med)). Men hoppas kunna bredda det till bättre för att parasa PDF:er som jag gjort är inte hundra... 

Det finns ett separat biblotek **PDF Clown** som täcks av LGPL, koden till PDF Clown kan hittas på [http://www.pdfclown.org](http://www.pdfclown.org). Detta biblotek används för att kunna läsa av PDF:erna innan de parsars. Under tidiga skeden har jag testat ca 5 olika innn jag bestämde mig för PDF Clown, finns en del testkod för dem som ligger kvar. Då PDF Clown inte finns till Maven eller dylikt så är en jar fil som länkar samman det, så testa att ändra sökvägen om prodjektet inte funkar för dig. Skulle gärna rensa upp mer kanse skaffa ett bygsystem kom gärna med förslag!

##Hur man går tillväga för att köra
Lättast är att ladda ner senaste versionen som jag sett till att bygga samman och laddat upp i form av en Jar fil. Som går att hitta under **release**.  [direktlänk här]( https://github.com/flaime/ResultatTavalaKanot/releases)

Annars för abolut senaste fixarna trixarna osv så får man bygga själv detta är ett **eclips/maven** prodjekt så kör in det där så ska det vara körbart på en gång. Jag ser till att inte puscha saker som gör att något går sönder (i alla fall inte som jag märker) och inte i alla fall till master branchen :)

## Hjälpa till
Om du kan och vill så får du hämst gärna hjälp till hör av dig eller ta och puscha något vet ja! :smiley:


##Onödig statestik
Hittade en liten rolig sak för att beräkna antal rader kod inte den bästa räkninge och säger väll inte så mycket men kan vara lite rolig: verkar dock inte funka så får rycka... :( 

<script src="http://code.jquery.com/jquery-1.4.2.min.js"></script>
<script>
'use strict';
//replace jquery/jquery with the repo you're interested in
//https://api.github.com/flaime/ResultatTavalaKanot/stats/contributors
fetch('https://api.github.com/repos/flaime/ResultatTavalaKanot/stats/contributors') //https://api.github.com/repos/jquery/jquery/stats/contributors')
    .then(response => response.json())
    .then(contributors => contributors
        .map(contributor => contributor.weeks
            .reduce((lineCount, week) => lineCount + week.a - week.d, 0)))
    .then(lineCounts => lineCounts.reduce((lineTotal, lineCount) => lineTotal + lineCount))
    .then(lines => window.alert(lines));
    document.write(5 + 6);
</script>
