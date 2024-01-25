# Uživatelská multimediální databáze

Autoři: Petr Jakubec, Vít Miikue Horčička, Lukáš Tománek


## Zadání

V tomto projektu vytvoříme databázi, kterou si uživatel bude moct procházet, hodnotit filmy, hudbu, hry a seriály. Program bude mít základní databázi v textovém souboru, který bude program na začátku načítat a před ukončením se do tohoto souboru uloží veškeré změny. Uživatel bude moct vyhledat média podle žánru nebo autorů a seřadit si je podle hodnocení a délky. Každé médium bude možné si prohlédnout a zobrazit si jeho informace jako je autor, stručný obsah nebo hodnocení.

## Popis funkcionality

### Třída `Database`

- Spravuje databázi a seznamy položek médií.
- Zajišťuje načítání a ukládání databáze.
- Poskytuje metody pro přidávání a odebírání položek médií a tvůrců.

### Třída `Media` (Abstraktní)

- Abstraktní třída sloužící jako základ pro všechny typy médií.
- Obsahuje společné atributy, jako jsou jméno, autor, žánr, hodnocení, rok vydání, délka a tagy.
- Jednotlivé žánry jsou napsané v enum `Genre`.
- Poskytuje metody pro nastavení a získání atributů médií.

### Třída `Creator`

- Reprezentuje autory nebo tvůrce médií.
- Zahrnuje detaily, jako jsou jméno, rok narození a typ (např. režisér, herec) - řešeno pomocí enum `Creators`.

### Třída `Main`

- Hlavní třída, kde dochází k interakci s uživatelem a spuštění programu.
- Zobrazuje menu, ve kterém si uživatel může prohlížet, přidávat nebo ukončit aplikaci.
- Obsahuje metody pro vytváření položek médií a autorů na základě vstupu uživatele.

### Třídy `Cinema`, `Series`, `Music`, `VideoGame`

- Rozšiřují třídu `Media`.
- Zobrazují a uchovávají dodatečné informace podle typu média.
- Platformy ve třídě `VideoGame` jsou uchovány v enum `Platforms`.

## Uživatelské rozhraní

- **Menu:** Uživatelé mají k dispozici menu s možnostmi pro prohlížení médií, přidání nových médií nebo ukončení aplikace.
- **Přidání Médií:** Uživatelé mohou přidávat nové položky médií specifikováním typu (film, seriál, hudba nebo videohra) a poskytnutím relevantních údajů.
- **Ukončení Aplikace:** Uživatelé mohou ukončit aplikaci, což vyvolá uložení databáze.

## Uživatelský Vstup a Výstup

- **Výběr z Menu:** Uživatelé zadávají čísla pro výběr možností z menu.
- **Přidání Médií:** Uživatelé poskytují detaily, jako jsou jméno, autor, žánr, hodnocení, rok vydání, délka, stručný popis a štítky pro médium. Uživatel je postupně vyzýván přidávání jednotlivých údajů pomocí yes/no dotazů.
- **Přidání Tvůrců:** Uživatelé zadávají údaje pro vytváření nových autorů, včetně jména, roku narození a typu.

## Podíl prací na projektu

Celý návrh projektu byl vytvořen dohromady na společné skupině, na které také probíhala hlavní komunikace týkající se projektu. Od začátku byl projekt rozdělen do několika částí, každý měl na starosti tu svojí.

- **Petr Jakubec:** Třída `Media`, třída `Creator`, třída `Main`, vyhledávání, celé uživatelské rozhraní.
- **Vít Miikue Horčička:** Třída `Database` a celková funkcionalita ukládání a načítání ze souboru.
- **Lukáš Tománek:** Třídy `Cinema`, `Music`, `Series`, `Videogames`, výpis informací v třídě `Media` + závěrečná zpráva.
