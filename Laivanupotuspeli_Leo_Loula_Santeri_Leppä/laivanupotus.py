import random

# Pelialustan luominen
lauta = []
for i in range(10):
    lauta.append(["O"]*10)


def aseta_laivat(lauta, pituus, asento, x, y, laiva_tyyppi):
    # Laivojen kirjainten määritys pelilaudalle
    if laiva_tyyppi == "lentotukialus":
        laiva_merkki = "L"
    elif laiva_tyyppi == "taistelulaiva":
        laiva_merkki = "T"
    elif laiva_tyyppi == "risteilija":
        laiva_merkki = "R"
    elif laiva_tyyppi == "risteilija2":
        laiva_merkki = "K"
    elif laiva_tyyppi == "havittaja":
        laiva_merkki = "H"
    elif laiva_tyyppi == "sukellusvene":
        laiva_merkki = "S"
    else:
        laiva_merkki = "S"

    # Laivan sijoittaminen pelilaudalle
    if asento == "vaaka":
        for i in range(pituus):
            lauta[y][x + i] = laiva_merkki
    elif asento == "pysty":
        for i in range(pituus):
            lauta[y + i][x] = laiva_merkki

# Generoidaan satunnaiset koordinaatit laivalle sen pituuden mukaan.


def generoi_koordinaatit(lauta, pituus):
    # Generoidaan satunnainen asento (vaaka tai pysty)
    asento = random.choice(["vaaka", "pysty"])

    if asento == "vaaka":
        x = random.randint(0, 9 - pituus)
        y = random.randint(0, 9)
    elif asento == "pysty":
        x = random.randint(0, 9)
        y = random.randint(0, 9 - pituus)

    # Tarkistetaan, onko toinen laiva jo ruudussa.
    if asento == "vaaka":
        for i in range(pituus):
            if lauta[y][x + i] != "O":
                return False
    elif asento == "pysty":
        for i in range(pituus):
            if lauta[y + i][x] != "O":
                return False

    # Jos kordinaatit ovat sallittuja, palauta ne.
    return (asento, x, y)


# Sijoita kaikki 5 laivaa pelialustalle.
ships = [(5, "lentotukialus"), (4, "taistelulaiva"), (3, "risteilija"),
         (3, "risteilija2"), (2, "havittaja"), (1, "sukellusvene")]
for ship in ships:
    koordinaatit = False
    while koordinaatit == False:
        koordinaatit = generoi_koordinaatit(lauta, ship[0])

    aseta_laivat(lauta, ship[0], koordinaatit[0],
                 koordinaatit[1], koordinaatit[2], ship[1])


def luo_botinnimi():
    # Lista merkkejä
    characters = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
                  'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9']

    # valitaan 4 satunnaista merkkiä
    random_string = "".join(random.choice(characters) for i in range(4))

    return random_string


botinnimi = luo_botinnimi()
Botti = {
    "Nimi": botinnimi
}


# --- Pelaaja tekee oman laudan ------

def generate_koordinaatit2(lauta, pituus):
    # Generoidaan satunnainen asento (vaaka tai pysty) käyttäen pelaajan syötettä
    asento = input("vaaka vai pysty:")
    a = 0
    b = 0
    # Generoidaan satunnainen ruutu laivalle pelaajan mukaisesti
    if asento.lower() == "vaaka":
        a = 9-pituus
        x = input("X-koordinaatti väliltä 0-"+str(a)+": ")
        x = int(x)
        y = input("Y-koordinaatti väliltä 0-9: ")
        y = int(y)
    elif asento.lower() == "pysty":
        b = 9-pituus
        x = input("X-koordinaatti väliltä 0-9: ")
        x = int(x)
        y = input("Y-koordinaatti väliltä 0-"+str(b)+": ")
        y = int(y)

    # Tarkistetaan onko valittu merkki jo valittu
    if asento == "vaaka":
        for i in range(pituus):
            if lauta[y][x + i] != "O":
                return False
    elif asento == "pysty":
        for i in range(pituus):
            if lauta[y + i][x] != "O":
                return False

    # Jos kordinaatit ovat sallittuja, palauta ne.
    return (asento, x, y)


print("Tervetuloa pelaamaan laivanupotuspeliä.")
print("Sinua vastaan pelaa Botti nimeltä: " + Botti.get("Nimi"))
print("")
print("Sijoita laivat haluamallasi tavalla kentälle")


pelaaja_lauta = []
for i in range(10):
    pelaaja_lauta.append(["O"] * 10)

pelaajaships = [(5, "lentotukialus"), (4, "taistelulaiva"), (3,
                                                             "risteilija"), (3, "risteilija2"), (2, "havittaja"), (1, "sukellusvene")]
for ship in pelaajaships:
    for rivi in pelaaja_lauta:
        print(" ".join(rivi))
    koordinaatit = False
    while koordinaatit == False:
        koordinaatit = generate_koordinaatit2(pelaaja_lauta, ship[0])
    aseta_laivat(pelaaja_lauta, ship[0], koordinaatit[0],
                 koordinaatit[1], koordinaatit[2], ship[1])

# Tulosta pelilauta
for row in pelaaja_lauta:
    print(" ".join(row))


# ------- Peli alkaa ---------
osuma = False
panos_lauta = []
for i in range(10):
    panos_lauta.append(["O"]*10)

# Kysytään pelaajalta vastustajan laivan sijaintia vaikeusasteen määrittelemän monta kertaa.


def pelaajanvuoro(panos_lauta, lauta):
    xkoordinaatti = input("Anna panoksen x-koordinaatti 0-9:")
    xkoordinaatti = int(xkoordinaatti)
    ykoordinaatti = input("Anna panoksen y-koordinaatti 0-9:")
    ykoordinaatti = int(ykoordinaatti)
    if lauta[ykoordinaatti][xkoordinaatti] != "O" and lauta[ykoordinaatti][xkoordinaatti] != "X":
        panos_lauta[ykoordinaatti][xkoordinaatti] = "X"
        lauta[ykoordinaatti][xkoordinaatti] = "X"
    elif lauta[ykoordinaatti][xkoordinaatti] == "X":
        panos_lauta[ykoordinaatti][xkoordinaatti] = "X"
    else:
        panos_lauta[ykoordinaatti][xkoordinaatti] = "-"
    print("")


edellinenvuorox = -1
edellinenvuoroy = -1

# Suorittaa vaikeusasteen mukaisesti arvauksia pelaajan ruudulta.


def vastustajanvuoro(osuma, edellinenvuorox, edellinenvuoroy):
    xkoordinaatti = random.randint(0, 9)
    ykoordinaatti = random.randint(0, 9)
    if osuma == True:
        if edellinenvuorox != -1 or edellinenvuorox != 0:
            xkoordinaatti = edellinenvuorox-1
            ykoordinaatti = edellinenvuoroy
        if edellinenvuorox == 0:
            if edellinenvuoroy != -1 or edellinenvuoroy != 0:
                ykoordinaatti = edellinenvuoroy-1
                xkoordinaatti = edellinenvuorox
    while (pelaaja_lauta[ykoordinaatti][xkoordinaatti] == "-" or pelaaja_lauta[ykoordinaatti][xkoordinaatti] == "X"):
        xkoordinaatti = random.randint(0, 9)
        ykoordinaatti = random.randint(0, 9)

    if pelaaja_lauta[ykoordinaatti][xkoordinaatti] != "O":
        osuma = True
        edellinenvuorox = xkoordinaatti
        edellinenvuoroy = ykoordinaatti
        pelaaja_lauta[ykoordinaatti][xkoordinaatti] = "X"
        print("")
        return True, edellinenvuorox, edellinenvuoroy
    else:
        osuma = False
        pelaaja_lauta[ykoordinaatti][xkoordinaatti] = "-"
        print("")
        return False, edellinenvuorox, edellinenvuoroy


vaikeusaste = input("Anna vaikeusaste väliltä 1-15: ")
vaikeusaste = int(vaikeusaste)

# Tarkistetaan voittiko vastustaja vai pelaaja


def bottivoitto(pelaaja_lauta):
    for row in pelaaja_lauta:
        print(" ".join(row))
    print("")
    for y in range(10):
        for x in range(10):
            if pelaaja_lauta[y][x] != "O" and pelaaja_lauta[y][x] != "-" and pelaaja_lauta[y][x] != "X":
                return False
    return True

# Tarkistetaan voittiko vastustaja vai pelaaja


def pelaajavoitto(lauta):
    for rivi in lauta:
        for alkio in rivi:
            if alkio != "O" and alkio != "-" and alkio != "X":
                return False
    return True


# Pelin tarkastelua vastaava while-loop:
while (True):
    for i in range(vaikeusaste):
        a, b, c = vastustajanvuoro(osuma, edellinenvuorox, edellinenvuoroy)
        if (a == True):
            osuma = True
            edellinenvuorox = b
            edellinenvuoroy = c
        else:
            osuma = False
        for row in pelaaja_lauta:
            print(" ".join(row))
        print("")
        if bottivoitto(pelaaja_lauta) == True:
            print("Botti voitti pelin")
            break
    if bottivoitto(pelaaja_lauta) == True:
        print("Botti voitti pelin")
        break

    for row in panos_lauta:
        print(" ".join(row))
    print("")
    for i in range(3):
        pelaajanvuoro(panos_lauta, lauta)
        for row in panos_lauta:
            print(" ".join(row))
        print("")
        if pelaajavoitto(lauta) == True:
            print("Pelaaja voitti pelin")
            break
    if pelaajavoitto(lauta) == True:
        print("Pelaaja voitti pelin")
        break

print("Peli päättyi")

with open('tilastot.txt', 'r') as file:
    botinvoitot = file.read()
    botinvoitot = int(botinvoitot)
    if bottivoitto(pelaaja_lauta):
        botinvoitot = botinvoitot+1

with open('tilastot.txt', 'w') as file:
    file.write(str(botinvoitot))
file.close
