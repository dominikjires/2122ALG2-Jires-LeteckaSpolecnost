# 2122ALG2-Jires-LeteckaSpolecnost
## Popis problému (motivace)

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
