# Obec Tršice
Repozitář pro webové stránky / aplikaci obce (primárně Tršice).

## Nastavení

Aplikace v některých případech používá princip "Convention over Configuration", tzn. některé věci jsou nastaveny implicitně přímo v kódu a pro jejich případnou změnu je třeba upravit kód aplikace.

Cesty v aplikaci, která vyžadují nějaký stupeň ověření jsou nastaveny v `ApplicationBean::preparePaths`. Kdo má jaké oprávnění se pak nastavuje v administraci rolí `/administrace/role.jsf`.

Menu jsou uložena v systému jako články (`/administrace/clanky.jsf`) se speciálním ID:

* Hlavní menu `navigace`
* Uživatelské menu pro přihlášeného uživatele `navigace-<název role>`, kde `název role` je název, kterou má uživatelská role vytvořená v administraci rolí `/administrace/role.jsf`. Menu se pak zobrazí všem uživatelům, kteří mají přidělenu tuto roli.
* Lišta s odkazy pro desktop verzi `bar-navigace`.

Patička webu je uložena v článku `footer`.

Je třeba nastavit správný resource pro přístup k databázi. Aplikace očekává, že bude nastaveno _obecdb_. Toto je možné nastavit buď v souboru _/WEB-INF/resources.xml_ nebo přímo v _tomee.xml_ souboru. Zde je příklad nastavení pro HSQL databázi - v produkci pravděpodobně použijete jinou. 
Také je třeba nastavit _mailServer_ resource pro konfiguraci odesílání emailů.

```
<Resource id="obecdb" type="DataSource">
    JdbcDriver = org.hsqldb.jdbcDriver
    JdbcUrl = jdbc:hsqldb:file:hsqldb
</Resource>
<Resource id="mailServer" type="javax.mail.Session">
    mail.from=
    mail.smtp.port=25
    mail.smtp.host=
    mail.smtp.auth=true
    mail.transport.protocol=smtp
    mail.smtp.starttls.enable=true
    mail.smtp.user=
    mail.smtp.password=
	</Resource>
```

## Changelog

### Verze 1.8.3

* Odstraněna RSS upoutávka na Zpravodaj.

### Verze 1.8.2

* Důležitá zpráva - administrace.
* Patička webu jako článek.

### Verze 1.8.1

* Při vytváření aktuality je možnost prohledávat obrázky použité v minulosti.
* Timeout pro session 4 hodiny.
* Obec Tršice na • Obec Tršice.
* Řazení aktualit podle data aktualizace.
* robots.txt
* Nový vzhled pro menu button u desktop verze.
* Opraveno og:description v aktualitách.

### Verze 1.8

* Copyright v patičce lze upravovat v nastavení.
* Administrace zastupitelů - počet položek na stránce je nastaven na počet členů zastupitelstva.
* Redesign některých prvků, především na úvodní stránce.
* Přidán RSS kanál pro úřední desku.
* Při editaci článku se ukládá, jestli je editován jako HTML nebo formátovaný text.

### Verze 1.7.7

* Oprava generování RSS feedů.

### Verze 1.7.4

* Aktuality mají časovou platnost.
* Při resetu hesla se u emailové adresy nerozlišují velká a malá písmena.
* Nová verze Log4j.

### Verze 1.7.3

* Možnost konfigurace období pro odečet vody. Pokud není zadáno, formulář je skryt.

### Verze 1.7.2

* Obrázky v galerii na úvodní straně jsou konfigurovatelné.
* Přidán samostatný správce souborů s možností mazaní.
* Možnost resetu hesla uživatelem.

### Verze 1.7.1

* Obrázek pro záhlaví už není hardcoded, ale dá se nastavit v administraci.
* Opraveno řazení zastupitelů a barva položek v menu.
* Přidána možnost přihlašování k odběru hlášení rozhlasu.

### Verze 1.7

* Kompletně přepracovaná menu.
* Optimalizovaný obrázek v hlavičce webu.
* Přihlášený uživatel - odhlásit jen po kliku na ODHLÁSIT, po kliku na jméno na uživatelskou stránku.
* Počitadlo znaků při vyplňování názvu aktuality.
* U editace článků odkaz pro zobrazení článku.

### Verze 1.6.1

* Řazení zastupitelů dle priority.
* V přehledu uživatele jen 4 aktuality.
* Vzhled boxu s obrázkem pro aktualitu na úvodní stránce.

### Verze 1.6

* Na stránce uživatele základní nastavení a informace.
* Hlášení rozhlasu posíláno emailem.
* Nová stránka zastupitelstva.
* Oprava chyb.

### Verze 1.5.2

* Formulář pro odečet vody.
* Důležitá zpráva na úvodní straně.

### Verze 1.5

* U editace ankety zobrazit pod formulářem výsledky ankety.
* Do informačního servisu pro zastupitele dát přehled a výsledky anket.
* Přidat správu zastupitelů.
* Migrace z log4j 1 na log4j 2.

### Verze 1.4

* Přidány ankety.
* Změněno tlačítko menu, jiný content disposition pro události.

### Verze 1.3.3

* Upraveno třídění položek na úřední desce.
* Upraveno zobrazování položek z úřední desky na stránce rozpočtu,...
* Informační nápis u grafu čerpání rozpočtu, přídán odkaz na muzeum do footeru.

### Verze 1.3

* Ve WYSIWYG editoru se při vložení obrázku vloží i atribut "alt".
* Změněn vzhled menu.
* Graf plnění rozpočtu.
* Stránka s informacemi pro zastupitele (po přihlášení).
* Opravena chyba při přesměrování po přihlášení (mohla nastat jen za určitých okolností).
* Na úvodní stránce nyní načítá 10 položek z archivu hlášení rozhlasu.
* Fotky s odkazem na fotogalerii se načítají až ve chvíli, kdy na ně uživatel naroluje.
* Změněn design (jiné barvy, modernější logo, jiná fotka v záhlaví).

### Verze 1.2

* Zlepšena přístupnost pro znevýhodněné uživatele.
* Nová položka do menu UtilityReport.
* U desktop menu odebrána zbytečná položka úvod.

### Verze 1.1

* Opraveno generování ID pro článek v aktualitě.
* Kliknutím na logo obce přejít na úvodní stránku.
* RSS pro aktuality.
* RSS pro hlášení rozhlasu.
* Generování iCal exportu pro akce.
* U článků nepovinná pole pro obrázek a popis (kvůli OpenGraph).
* V administraci obecné nastavení.
* Několik menších oprav / vylepšení.

### Verze 1.0

* První dostupná verze.


