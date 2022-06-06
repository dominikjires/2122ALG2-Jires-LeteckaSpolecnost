# 2122ALG2-Jires-LeteckaSpolecnost

# Dokumentace
## Zadání práce 
### Popis problému (motivace)

Program LeteckaSpolecnost bude sloužit jako rezervační systém letecké společnosti, která bude létat z Prahy do několika evropských metropolí. Vstupem bude textový soubor obsahující seznam destinací a následně soubory obsahující jednotlivé lety do destinací a zpět (vzletové a přístávací letiště, číslo letu, čas odletu, čas příletu, typ letadla, registrace letadla a počet sedadel v letadle). Cestující zvolí destinaci, datum a příslušný let. Cestující bude zadávat:
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

Pokud kapacita letu nebude vyčerpána a informace budou zadány ve správnem formátu bude rezervace úspěšná a cestujícímu bude přiřazeno číslo rezervace a sedadlo. Výstupem bude pdf soubor pro každý jednotlivý let se setříděným seznamem všech cestujících, počtem zavazadel a vypočítanou vzletovou hmotností. Pro každého cestujícího bude vygenerovaný pdf soubor - boarding pass s informacemi o osobě, letem, sedadlem a časem nástupu do letadla (čas odletu minus 30 minut).

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
 - program čte soubor destinations.txt v `2122ALG2-Jires-LeteckaSpolecnost\data`, ve kterém jsou na řádcích vypsané obsluhované destinace, poté co přečte destinaci začne pročítat jednotlivé .txt soubory s názvem destinace (pokud soubor není vytvořen - nejsou přířazeny lety), v jednotlivých souborech je na každém řádku let ve tvaru date, flight number, departure airport, arrival airport, departure time, arrival time, type of aircraft, registration of aircraft oddělených bílými znaky
 - výstupem je pdf soubor palubní vstupenky cestujícího (ale pouze pokud o to v programu požádá) s informacemi o sedadle, času nástupu do letadla a letu
 - výstupem je také pdf soubor s informacemi o letu pro zaměstnance letecké společnosti a seznamem jednotlivých cestujících s počty zavazadel a dalšíma informacema (pouze pokud cestující provede odbavení a je přiřazen na let)
### Class diagram
![image](https://user-images.githubusercontent.com/100781365/172136798-8591c52c-73ea-49fc-a8c3-ad02aef3abeb.png)

## Testování

## Popis fungování externí knihovny
 - PDFBox
 - open-source knihovna napsaná v Javě
 - Umožňuje vytváření nových dokumentů PDF, manipulaci se stávajícími dokumenty, přidávání záložek do PDF a možnost extrahovat obsah z dokumentů PDF
 - JAR file
