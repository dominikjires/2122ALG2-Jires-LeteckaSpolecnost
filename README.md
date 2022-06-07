# 2122ALG2-Jires-LeteckaSpolecnost

# Dokumentace
## Zadání práce 
### Popis problému (motivace)

Program LeteckaSpolecnost bude sloužit jako rezervační systém letecké společnosti, která bude létat z Prahy do několika evropských metropolí. Vstupem bude textový soubor obsahující seznam destinací a následně soubory obsahující jednotlivé lety do destinací a zpět (vzletové a přístávací letiště, číslo letu, čas odletu, čas příletu, typ letadla a registrace letadla). Cestující zvolí destinaci, datum a příslušný let. Cestující bude zadávat:
- **povinně**:
  - pohlaví 
  - jméno
  - příjmení
  - datum narození
  - číslo občanského průkazu nebo pasu
- **nepovinně**:
  - počet zavazadel a hmotnost (max 2 zavazadla 15 nebo 25kg)
  - číslo věrnostního programu
  - předobjednané občerstvení
- **další atributy**:
  - informace o platbě (Boolean)

Pokud kapacita letu nebude vyčerpána a informace budou zadány ve správnem formátu bude rezervace úspěšná a cestujícímu bude přiřazeno číslo rezervace a sedadlo. Výstupem bude pdf soubor pro každý jednotlivý let se setříděným seznamem všech cestujících a počtem zavazadel. Pro každého cestujícího bude vygenerovaný pdf soubor - boarding pass s informacemi o osobě, letem, sedadlem a časem nástupu do letadla (čas odletu minus 30 minut).

## Řešení
### Funkční specifikace

Aplikace je tvořena pomocí hlavního menu a submenu. Uživatel zadává výběr pomocí čísla, požadovaného slova nebo charakteru. Nejříve uživatel může provést registraci na požadovaný let do požadované destinace. Pokud zaplatí  získá číslo rezervace a je mu umožněno dostat se do submenu, kde může let zrušit, vytvořit check-in (odbavení) a přidělení sedadla, nebo uložit palubní vstupenku pokud již check-in udělal. Do submenu se uživatel dostane také z halvního menu pokud zada svůj let do destinace a číslo registrace, pokud existuje. Z menu je tako možnost se dostat do nezákaznické sekce, kde je možno vyhledat let do požadované destinace a uložit seznam pasažérů.
- **hlavní menu**
  - `1` - vyhledat let
  - `2` - dostat se k rezervaci do submenu
  - `3` - sekce pro vytisknutí seznamu pasažérů (chráněno heslem)
  - `0` - exit
- **submenu**
  - `1` - odbavení (pokud ještě není provedeno)
  - `2` - zrušit let (pokud není provedeno odbavení)
  - `3` - vytvořit palubní vstupenku (pokud je provedeno odbavení)
  - `0` - exit do hlavního menu
- **vyhledání destinace**
  - `název destinace` - vyběr požadované destinace
- **vyhledání letu**
  - `číslo pořadí` - vyběr letu podle pořadí
- **vyhledání cestujícího**
  - `číslo pasažéra` - vyběr pasažéra
- **registrace pasažéra**
- `jméno`
- `příjmení`
- **zaplacení**
  - `y` - zaplatit
  - `n` - nezaplatit
- **odbavení**
  - `číslo 1 nebo 2 cifry` - den narození
  - `číslo 1 nebo 2 cifry` - měsíc narození
  - `číslo 4 cifry` - rok narození
  - `m`/`M` - muž
  - `f`/`F` - žena
  - `1` - cestovní pas
  - `2` - občanský průkaz
  - `číslo` - číslo občanského průkazu
  - `y` - chci zavazadlo
  - `n` - nechci zavazadlo
  - `20` - 20kg zavazadlo
  - `1` - 1 zavazadlo
  - `2` - 2 zavazadla
  - `y` - jsem členem věrnostního programu
  - `n` - nejsem členem věrnostního programu
  - `číslo` - číslo věrnostního programu
  - `y` - chci občerstvení
  - `n` - nechci občerstvení
  - `y` - chci uložit palubní vstupenku
  - `n` - nechci uložit palubní vstupenku

### Popis struktury vstupních a výstupních souborů
 - program čte soubor destinations.txt v `2122ALG2-Jires-LeteckaSpolecnost\data`, ve kterém jsou na řádcích vypsané obsluhované destinace, poté co přečte destinaci začne pročítat jednotlivé .txt soubory s názvem destinace (pokud soubor není vytvořen - nejsou přířazeny lety), jednotlivé soubory s lety obsahují:
   - datum, číslo letu, město odletu, město příletu, čas odletu, čas příletu, typ letadla a registrace letadla
 - výstupem je pdf soubor palubní vstupenky cestujícího (ale pouze pokud o to v programu požádá) s informacemi:
   - město odletu a příletu, číslo letu, datum letu, začátek nástupu do letadla (což je čas odletu - 30 min), sedadlo, jméno příjmení, pohlaví (enum Gender) M nebo F, typ dokumentu ID nebo Passport s číslem dokumentu, počet zavazadel a typ zavazadla small/large/no a číslo věrnostní kartičky
 - výstupem je také pdf soubor pro zaměstnance letecké společnosti (pouze pokud cestující provede odbavení a je přiřazen na let) s:
   - datumem, číslem letu, město odletu, město příletu, čas odletu a příletu, typ letadla
   - seznamem jednolivých cestujících setříděných podle příjmení (jméno, příjmení, enum Gender M nebo F, enum typ dokumentu ID nebo Passport s číslem dokumentu, počet zavazadel a enum typ zavazadla Small/Large/No, sedadlo a enum catering YES nebo NO) 
### Class diagram
![image](https://user-images.githubusercontent.com/100781365/172136798-8591c52c-73ea-49fc-a8c3-ad02aef3abeb.png)

## Testování
### 1. test - menu
 - v menu by mělo být umožněno zadání pouze číslic 0, 1, 2, 3 a zadaní např. Stringu by mělo napsat "Request not found" a znova po uživateli chtít zadat číslo
 - ![image](https://user-images.githubusercontent.com/100781365/172337107-871fcd8e-8251-423b-bbd3-71765f6fb939.png)
 - **test prošel**
### 2. test - velká/malá písmena
 - Ve výběru destinace by mělo být uživateli umožněno napsat destinaci malými i velkými písmeny
 - ![image](https://user-images.githubusercontent.com/100781365/172337942-f90b2fa0-d95a-441e-b248-19dbe230c12f.png)
 - **test prošel**
### 3. test - zakázané znaky
 - Při vyplňování jména (i příjmení) by nemělo uživateli být umožněno vyplnit znaky mimo ASCII (háčky čárky) a program by měl vyžadovat opakování zadání
 - ![image](https://user-images.githubusercontent.com/100781365/172338770-9cd70b18-7f2a-4913-b16d-552278ff8c5d.png)
 - **test prošel**
### 4. test - placení
 - Pokud uživatel nezaplatí při rezervaci letu, mělo by mu být umožněno (a hlavně vyžadováno) zaplacení ve správě rezervace a dokud nezaplatí, nedostane se k odbavení
 - ![image](https://user-images.githubusercontent.com/100781365/172339661-b6b6ad15-3398-4b0a-aa1e-99366685e6f8.png)
 - **test prošel**
### 5. test - generování palubní vsupenky
 - Dokud uživatel neprovede odbavení, neměl by se dostat ke generování palubní vstupenky
 - ![image](https://user-images.githubusercontent.com/100781365/172340132-0dac579b-66f5-4f5b-a7fd-5bec49542067.png)
 - **test prošel**
### 6. test - datum narození
 - Nemělo by být povoleno zadat do datumu narození cokoliv jiného než číslo (int) v požadovaném rozsahu
 - ![image](https://user-images.githubusercontent.com/100781365/172340748-4b3ecb29-d126-42de-9b11-26e137f2d3cd.png)
 - **test prošel**
### 7. test - pdf soubor
 - Po dokončení odbavení by se měl program zeptat, zda uživatel požaduje uložení palubní vsupenky do pdf a palubní vstupenku by měl v pdf uložit
 - ![image](https://user-images.githubusercontent.com/100781365/172342044-641b960c-8fed-4281-b5b3-64733d0745ee.png)
 - **test prošel**
### 8. test - znovuodbavení
 - Má být zakázán přístup po odbavení do sekce odbavení
 - ![image](https://user-images.githubusercontent.com/100781365/172342335-c9bb5eb4-97be-4678-8b14-80912dcbd006.png)
 - **test prošel**
### 9. test - zrušení letu
 - Nemá být povoleno zrušit let po odbavení
 - ![image](https://user-images.githubusercontent.com/100781365/172342585-af79008d-827b-4f22-aea1-662cbd37a6d7.png)
 - **test prošel**
### 10. test - znovuvyhledání rezervace
 - Uživateli má být umožněno znovu vyhledat rezervaci
 - ![image](https://user-images.githubusercontent.com/100781365/172344679-865bebd0-28e5-4693-af26-09e4183ab639.png)
 - **test prošel**
## Popis fungování externí knihovny
 - PDFBox
 - open-source knihovna napsaná v Javě
 - Umožňuje vytváření nových dokumentů PDF, manipulaci se stávajícími dokumenty, přidávání záložek do PDF a možnost extrahovat obsah z dokumentů PDF
 - JAR file
