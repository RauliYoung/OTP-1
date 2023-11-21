# Sovelluksen kuvantamiseen käytettyjä kaavioita
---
### Luokkakaavio

Luokkaaviolla kuvataan ohjelman koostuminen luokista.

![img_1.png](diagrams/img_1.png)

![img_2.png](diagrams/img_2.png)

![img_3.png](diagrams/img_3.png)

![img_4.png](diagrams/img_4.png)

![img_5.png](diagrams/img_5.png)

---

### Sekvenssikaavio
Sekvenssikaavio mallintaa sovelluksen sisäistä toimintaa käyttötapauksittain.

![img.png](diagrams/img.png)

1. Käyttäjä luo käyttäjätunnuksen. Jos käyttäjätunnuksen luonnissa tapahtuu virhe, niin
   palataan rekisteröitymissivulle, muuten jatketaan kirjautumisikkunaan.
2. Jos käyttäjätunnuksen luonti onnistui, yritetään kirjautua sovellukseen.
3. Jos kirjautumistiedot ovat oikein, siirrytään kotinäkymään, muussa tapauksessa palataan
   kirjautumisikkunaan.

---

### Käyttötapauskaavio

Käyttötapauskaavio mallintaa sovelluksen vuorovaikutusta ympäristön kanssa.

Vuorovaikutuksia ovat mm. syötteet ja tulosteet mitä ohjelmaa saa ja tarjoaa.

![img_7.png](diagrams/img_7.png)

---

### Sijoittelukaavio

Sijoittelukaavio kuvantaa laitteistoa jolla järjestelmän on tarkoitus toimia. Kaavio osoittaa riippuvuudet ja tiedonsiirto tarpeet eri laitteiden välillä.

![img_8.png](diagrams/img_8.png)
---

### Aktiviteettikaavio

Aktiviteettikaavio mallintaa järjestelmän dynaamista käyttäytymista, eli tarkka suorituspolku selviää mahdollisesti vasta suorituksen aikana.

![img_6.png](diagrams/img_6.png)

---
