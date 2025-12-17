package com.hotel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * Gestió de reserves d'un hotel.
 */
public class App {

    // --------- CONSTANTS I VARIABLES GLOBALS ---------

    // Tipus d'habitació
    public static final String TIPUS_ESTANDARD = "Estàndard";
    public static final String TIPUS_SUITE = "Suite";
    public static final String TIPUS_DELUXE = "Deluxe";

    // Serveis addicionals
    public static final String SERVEI_ESMORZAR = "Esmorzar";
    public static final String SERVEI_GIMNAS = "Gimnàs";
    public static final String SERVEI_SPA = "Spa";
    public static final String SERVEI_PISCINA = "Piscina";

    // Capacitat inicial
    public static final int CAPACITAT_ESTANDARD = 30;
    public static final int CAPACITAT_SUITE = 20;
    public static final int CAPACITAT_DELUXE = 10;

    // IVA
    public static final float IVA = 0.21f;

    // Scanner únic
    public static Scanner sc = new Scanner(System.in);

    // HashMaps de consulta
    public static HashMap<String, Float> preusHabitacions = new HashMap<String, Float>();
    public static HashMap<String, Integer> capacitatInicial = new HashMap<String, Integer>();
    public static HashMap<String, Float> preusServeis = new HashMap<String, Float>();

    // HashMaps dinàmics
    public static HashMap<String, Integer> disponibilitatHabitacions = new HashMap<String, Integer>();
    public static HashMap<Integer, ArrayList<String>> reserves = new HashMap<Integer, ArrayList<String>>();

    // Generador de nombres aleatoris per als codis de reserva
    public static Random random = new Random();

    // --------- MÈTODE MAIN ---------

    /**
     * Mètode principal. Mostra el menú en un bucle i gestiona l'opció triada
     * fins que l'usuari decideix eixir.
     */
    public static void main(String[] args) {
        inicialitzarPreus();

        int opcio = 0;
        do {
            mostrarMenu();
            opcio = llegirEnter("Seleccione una opció: ");
            gestionarOpcio(opcio);
        } while (opcio != 6);

        System.out.println("Eixint del sistema... Gràcies per utilitzar el gestor de reserves!");
    }

    // --------- MÈTODES DEMANATS ---------

    /**
     * Configura els preus de les habitacions, serveis addicionals i
     * les capacitats inicials en els HashMaps corresponents.
     */
    public static void inicialitzarPreus() {
        // Preus habitacions
        preusHabitacions.put(TIPUS_ESTANDARD, 50f);
        preusHabitacions.put(TIPUS_SUITE, 100f);
        preusHabitacions.put(TIPUS_DELUXE, 150f);

        // Capacitats inicials
        capacitatInicial.put(TIPUS_ESTANDARD, CAPACITAT_ESTANDARD);
        capacitatInicial.put(TIPUS_SUITE, CAPACITAT_SUITE);
        capacitatInicial.put(TIPUS_DELUXE, CAPACITAT_DELUXE);

        // Disponibilitat inicial (comença igual que la capacitat)
        disponibilitatHabitacions.put(TIPUS_ESTANDARD, CAPACITAT_ESTANDARD);
        disponibilitatHabitacions.put(TIPUS_SUITE, CAPACITAT_SUITE);
        disponibilitatHabitacions.put(TIPUS_DELUXE, CAPACITAT_DELUXE);

        // Preus serveis
        preusServeis.put(SERVEI_ESMORZAR, 10f);
        preusServeis.put(SERVEI_GIMNAS, 15f);
        preusServeis.put(SERVEI_SPA, 20f);
        preusServeis.put(SERVEI_PISCINA, 25f);
    }

    /**
     * Mostra el menú principal amb les opcions disponibles per a l'usuari.
     */
    public static void mostrarMenu() {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. Reservar una habitació");
        System.out.println("2. Alliberar una habitació");
        System.out.println("3. Consultar disponibilitat");
        System.out.println("4. Llistar reserves per tipus");
        System.out.println("5. Obtindre una reserva");
        System.out.println("6. Ixir");
    }

    /**
     * Processa l'opció seleccionada per l'usuari i crida el mètode corresponent.
     */
    public static void gestionarOpcio(int opcio) {
       //TODO:
       switch (opcio) {
        case 1:
            reservarHabitacio();
            break;

        case 2:
            alliberarHabitacio();
            break;

        case 3:
            consultarDisponibilitat();
            break;

        case 4:
            obtindreReservaPerTipus();
            break;

        case 5:
            obtindreReserva();
            break;
       
        case 6: 
            break;
            
        default:
            System.out.println("La opció triada no es correcta. Tria altra!");
            break;
       }
    }

    /**
     * Gestiona tot el procés de reserva: selecció del tipus d'habitació,
     * serveis addicionals, càlcul del preu total i generació del codi de reserva.
     */
    public static void reservarHabitacio() {
        System.out.println("\n===== RESERVAR HABITACIÓ =====");
        //TODO:

        //Aquí es donde vamos a conectar todas las funciones y mostrar los mensajes al clientes de esas funciones. 
        //Esta función es la que llama a todas las funciones que preguntan al cliente, las que necesitamos para gestionar la reserva.
        //Cada función llamada nos indica el tipo de función y lo que nos devuelve

        String tipusHabitacio = seleccionarTipusHabitacioDisponible(); //Creamos variable para guardar el tipo de habitación que el cliente haya seleccionado.
        if (tipusHabitacio == null) {
            return;  //reservarHabitacio es void, quiere decir que es un valor que no devuelve nada,. Si no hay disponibilidad el programa se interrumpe y no sigue la ejecución. (Se para la reserva)
        }

        ArrayList<String> serveisSeleccionats = seleccionarServeis(); //Únicamente llamámos a la función de los servicios adicionales, que es nos devolvía un array list de tipo String. Por que la validación ya está hecha en su correspondiente función.
                                                                      //La lista se guarda en serveisSeleccionats.

        float precioTotalReserva = calcularPreuTotal(tipusHabitacio, serveisSeleccionats); //Llamamos a la función que calculaba el precio total calcularPreutotal , con la habitación y los servicios añadidos que se encuentran en las dos variables creadas anteriores.

        System.out.println("\nPreu total de la reserva: " + precioTotalReserva); //Mostramos el precio total de la reserva
        
        int codigoReserva = generarCodiReserva(); //Guardamos el código reserva llamándo a su función.

        //Repasar! Complicado
        //Guardamos la reseva, necesitamos un arraylist con la lista de la habitación, servicio y precio. 
        // Para el servicio añadido usuaremos metodo StringvalueOf(); es un método de la clase String para convertir tipos primitivos en cadenas de texto.
        ArrayList<String> datosReserva = new ArrayList<>();
        datosReserva.add(tipusHabitacio);    //Añadimos el tipo de habitación como primer elemento
        datosReserva.add(String.valueOf(precioTotalReserva)); //Añade el precio total de la reserva, el métdodo convierte el float en texto.
        
        //Añadimos los servicios de la reserva.
        for (String servicios : serveisSeleccionats) {  //Recorremos los servicios seleccionados
            datosReserva.add(servicios);                 //Los añadimos a los datos de la reserva
        }

        //Guardamos la reserva en el Hasmap reserva
        reserves.put(codigoReserva, datosReserva); //El código es la clave y datosReseva el valor
        
        //Usamos el hasmap para que nos diga el la disponibilidad de habitaciones, 
        //Lo guardaremos en una variable int , ya que el valor que devuelve el hasmap es un integer
        int disponibilidad = disponibilitatHabitacions.get(tipusHabitacio);

        //restamos 1 al número de habitaciones , porque si un cliente reserva una habitación hay una menos disponible y eso se tiene que guardar en el sistema.
        int actualDisponibilidad = disponibilidad -1;  //operación aritmética

        //Guardamos la nueva actualDisponibilidad en el hashMap disponibilitatHabitacions
        disponibilitatHabitacions.put(tipusHabitacio,actualDisponibilidad);


        System.out.println("\nEl seu codi de reserva es: " + codigoReserva);
    }

    /**
     * Pregunta a l'usuari un tipus d'habitació en format numèric i
     * retorna el nom del tipus.
     */
    public static String seleccionarTipusHabitacio() {
        //TODO:
        
        System.out.println("\nTipus d'habitacions:");
        System.out.println("1. " + TIPUS_ESTANDARD);
        System.out.println("2. " + TIPUS_SUITE);
        System.out.println("3. " + TIPUS_DELUXE);
        System.out.println("Per favor, tria el tipus d'habitació. ");
        
        int opcio = llegirEnter("Número: "); //Variable creada para leer el entero

        while (opcio < 1 || opcio > 3) {
            System.out.println("Opció no vàlida");
            opcio = llegirEnter("Torna a triar entre 1, 2 o 3: ");
        }
        
        String tipus = null;
        if (opcio == 1) {
            tipus = TIPUS_ESTANDARD;
        } else if (opcio == 2) {
            tipus = TIPUS_SUITE;
        } else if (opcio == 3) {
            tipus = TIPUS_DELUXE;
        }

        return tipus;
    }

    /**
     * Mostra la disponibilitat i el preu de cada tipus d'habitació,
     * demana a l'usuari un tipus i només el retorna si encara hi ha
     * habitacions disponibles. En cas contrari, retorna null.
     */
    public static String seleccionarTipusHabitacioDisponible() {
        System.out.println("\nDisponibilitat d'habitacions:");
        //TODO:
        mostrarInfoTipus(TIPUS_ESTANDARD);
        mostrarInfoTipus(TIPUS_SUITE);
        mostrarInfoTipus(TIPUS_DELUXE);

        String tipustriat = seleccionarTipusHabitacio();

        int cuantesDispo = disponibilitatHabitacions.get(tipustriat);
        if (cuantesDispo > 0) {
            return tipustriat;
        } else {
            System.out.println("No queden habitacions d'eixe tipus");
            return null;
        }

    }

    /**
     * Permet triar serveis addicionals (entre 0 i 4, sense repetir) i
     * els retorna en un ArrayList de String.
     */
    public static ArrayList<String> seleccionarServeis() {
        //TODO:

        //Mensaje para el usuario:
        System.out.println("\nServeis adicionals: ");
        System.out.println("1. " + SERVEI_ESMORZAR);
        System.out.println("2. " + SERVEI_GIMNAS);
        System.out.println("3. " + SERVEI_SPA);
        System.out.println("4. " + SERVEI_PISCINA);

        ArrayList<String> triaServeis = new ArrayList<>();

        int opcioServei = llegirEnter("\nTria un servei adicional: "); //Se crea la variable tipo entero para leer/guardar el número que ponga el usuario. Usando el método auxiliar.

        while (opcioServei > 0 && triaServeis.size() < 4 ) { //Mientras elija más de 0 y no sean más de 4, repite el bucle, sigue pidiendo. //size controla el limite de los servicios , que son 4 cómo máximo.
            
            switch (opcioServei) { //Uso un switch para traducir el número que hna puesto el usuario.
                case 1:
                    //Necesitamos un if para comprobar si los servicios elegidos se repiten o no, ya que no se pueden repertir. Lo aplicamos en los 4 casos:
                    if (triaServeis.contains(SERVEI_ESMORZAR)) { //Si la lista triaServeis contiene SERVEIS_ESMORZAR..
                        System.out.println("Servei ja triat");
                    } else {
                        triaServeis.add(SERVEI_ESMORZAR); //Si no está en la lista, añadirlo.
                    }
                    break;
                    
                case 2:
                    if (triaServeis.contains(SERVEI_GIMNAS)) {
                        System.out.println("Servei ja triat");
                    } else {
                        triaServeis.add(SERVEI_GIMNAS);
                    }
                    break;
                
                case 3:
                     if (triaServeis.contains(SERVEI_SPA)) {
                        System.out.println("Servei ja triat");
                    } else {
                        triaServeis.add(SERVEI_SPA);
                    }
                    break;

                case 4:
                    if (triaServeis.contains(SERVEI_PISCINA)) {
                        System.out.println("Servei ja triat");
                    } else {
                        triaServeis.add(SERVEI_PISCINA);
                    }
                    break;

                default:
                    System.out.println("\nLa opció triada no es vàlida. ");
                    break;

            }
            //Preguntar al usuario si quiere añadir otro servicio: //Si el usuario pone un 0 , sale del bucle porque NO cumple la condición del while.
            opcioServei = llegirEnter("\nVols afegir altre servei? ");

        }
        return triaServeis; //Devolvemos la lista
    }

    /**
     * Calcula i retorna el cost total de la reserva, incloent l'habitació,
     * els serveis seleccionats i l'IVA.
     */
    public static float calcularPreuTotal(String tipusHabitacio, ArrayList<String> serveisSeleccionats) { 
        //TODO:
        //## Repasar!, esta es una de las que más me ha costado

        float precioTotal = 0;  //Primero necesitamos una variable que acumule el precio total (Habitación, servicios, IVA). La iniciamos a 0.
        float precioHabitacion = preusHabitacions.get(tipusHabitacio); //Creamos una variable que guarde el precio de la habitación seleccionada. Necesitamos get para obtener el precio del tipo de habitación.
        
        precioTotal = precioTotal + precioHabitacion; //Hay que sumar el precio de la habitación al precio total , para que el total vaya teniendo algún valor, para ello actualizamos la variable precioTotal
        
        //
        for (String servicio : serveisSeleccionats) {  //servicio es la variable que recibe cada servicio seleccionado del ArrayList mientras lo recorremos. Es String por que ese array es una lista de tipo string.
            float precioServicio =  preusServeis.get(servicio); //Se crea una variable para guardar el precio del servicio , éste nos lo da preuServeis usando el método get. Que previamente se guardó en servicio.
            precioTotal = precioTotal + precioServicio; //Sumamos (actualizamos) el precio del servicio a la variable del precio total
        }

        precioTotal = precioTotal + (precioTotal * IVA); //Para el IVA , multiplicamos el precio total por el IVA. Se pone entre () por que esa operación tiene que hacerse primero y luego hacemos la suma.

        return precioTotal;
    }

    /**
     * Genera i retorna un codi de reserva únic de tres xifres
     * (entre 100 i 999) que no estiga repetit.
     */
    public static int generarCodiReserva() {
        //TODO:
        //Aquí necesitamos el objeto random de la clase Random, que es la que java usa para generar números aleatorioso.
        //Y llamaremos a reservers para comprobar que el código de reserva no se repita
        int codigo = 100 + random.nextInt(900); //Creamos variable codigo para almacenar un código generado aleatorio por random entre 100 y 900
        while (reserves.containsKey(codigo)) { //Mientras el código se repita en reserves, genera otro codigo 
            codigo = 100 + random.nextInt(900);
        }
        
        return codigo; //Devuelve el código
    }

    /**
     * Permet alliberar una habitació utilitzant el codi de reserva
     * i actualitza la disponibilitat.
     */
    public static void alliberarHabitacio() {
        System.out.println("\n===== ALLIBERAR HABITACIÓ =====");
         // TODO: Demanar codi, tornar habitació i eliminar reserva

    }

    /**
     * Mostra la disponibilitat actual de les habitacions (lliures i ocupades).
     */
    public static void consultarDisponibilitat() {
        // TODO: Mostrar lliures i ocupades
    }

    /**
     * Funció recursiva. Mostra les dades de totes les reserves
     * associades a un tipus d'habitació.
     */
    public static void llistarReservesPerTipus(int[] codis, String tipus) {
         // TODO: Implementar recursivitat
    }

    /**
     * Permet consultar els detalls d'una reserva introduint el codi.
     */
    public static void obtindreReserva() {
        System.out.println("\n===== CONSULTAR RESERVA =====");
        // TODO: Mostrar dades d'una reserva concreta
 
    }

    /**
     * Mostra totes les reserves existents per a un tipus d'habitació
     * específic.
     */
    public static void obtindreReservaPerTipus() {
        System.out.println("\n===== CONSULTAR RESERVES PER TIPUS =====");
        // TODO: Llistar reserves per tipus
    }

    /**
     * Consulta i mostra en detall la informació d'una reserva.
     */
    public static void mostrarDadesReserva(int codi) {
       // TODO: Imprimir tota la informació d'una reserva
    }

    // --------- MÈTODES AUXILIARS (PER MILLORAR LEGIBILITAT) ---------

    /**
     * Llig un enter per teclat mostrant un missatge i gestiona possibles
     * errors d'entrada.
     */
    static int llegirEnter(String missatge) {
        int valor = 0;
        boolean correcte = false;
        while (!correcte) {
                System.out.print(missatge);
                valor = sc.nextInt();
                correcte = true;
        }
        return valor;
    }

    /**
     * Mostra per pantalla informació d'un tipus d'habitació: preu i
     * habitacions disponibles.
     */
    static void mostrarInfoTipus(String tipus) {
        int disponibles = disponibilitatHabitacions.get(tipus);
        int capacitat = capacitatInicial.get(tipus);
        float preu = preusHabitacions.get(tipus);
        System.out.println("- " + tipus + " (" + disponibles + " disponibles de " + capacitat + ") - " + preu + "€");
    }

    /**
     * Mostra la disponibilitat (lliures i ocupades) d'un tipus d'habitació.
     */
    static void mostrarDisponibilitatTipus(String tipus) {
        int lliures = disponibilitatHabitacions.get(tipus);
        int capacitat = capacitatInicial.get(tipus);
        int ocupades = capacitat - lliures;

        String etiqueta = tipus;
        if (etiqueta.length() < 8) {
            etiqueta = etiqueta + "\t"; // per a quadrar la taula
        }

        System.out.println(etiqueta + "\t" + lliures + "\t" + ocupades);
    }
}
