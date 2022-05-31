# Proiect Calitatea Sistemelor Software

## Componenta Echipei
* Andra Simion - ISS1
* Camelia Stativa - ISS1
* Daniel Rusu - ISS1

Acest proiect are ca scop realizarea de calcule aritmetice a expresiilor ce folosesc variabile cu valori definite de utilizator.

Sunt doua modalitati de evaluare a expresiilor: de la tastatura (din consola) sau dintr-un fisier .xml.
In ambele moduri, pentru a defini o expresie, exista anumite constrangeri:
* Pentru a realiza operatii cu radical, trebuie sa precedam variabilele noastre cu caracterul 'S' (e.g. Sa + Sb = radical din a plus radical din b)
* Pentru ridicarea la putere se foloseste semnul '^' (e.g. a^b = a ridicat la puterea b)

Cateva exemple de expresii:
* (a+b) = adunarea lui a cu b
* ((a^b-c)+(a*b))
* (Sa+Sb) = adunarea lui radical din a + radical din b

Exista un fisier test.xml in care puteti vedea un model de cum se definesc variabilele si valorile lor, dar si expresia.

Toata logica de implementare a operatiilor se regasesc in clasa BigNumber.java. (Implementare de catre Daniel, Camelia, Andra)
Clasa care testeaza functionalitatile de aici este BigNumberShould.java (Implementare de catre Camelia)

Toata logica de parsare dintr-un String intr-o expresie, cat si extragerea token-urilor/variabilelor, se regaseste in Parser.java (Implementare de catre Camelia si Andra)
Clasa care testeaza functionalitatea din Parser.java este ParserTest.java (Implementare de catre Camelia)

Toate fisierele din package-ul xml implementeaza logica de citire si extragere a datelor din fisierul xml atunci cand programul executa modul de citire din fisier. (Implementare de catre Daniel)

Clasele care testeaza functionalitatea de citire si extragere a datelor din XML sunt XMLReaderTest, XMLParserTest, XMLValidatorTest (Implementare facuta de catre Andra si Daniel).
