import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OrdenamientoBusqueda {

    public static BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));

    //Lee un entero validando la entrada; repite la pregunta mientras el dato sea inválido o menor al mínimo.
    public static int leerEntero(String mensaje, int minimo) throws IOException {
        int valor = 0;
        boolean valido = false;

        while (!valido) {
            System.out.print(mensaje);
            try {
                valor = Integer.parseInt(entrada.readLine().trim());
                if (valor >= minimo) {
                    valido = true;
                } else {
                    System.out.println("Debe ingresar un número mayor o igual a " + minimo + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ingresar un número entero.");
            }
        }
        return valor;
    }

    //Lee una palabra no vacía; repite la pregunta mientras el usuario no escriba nada.
    public static String leerPalabra(String mensaje) throws IOException {
        String palabra = "";

        while (palabra.isEmpty()) {
            System.out.print(mensaje);
            palabra = entrada.readLine().trim();
            if (palabra.isEmpty()) {
                System.out.println("La palabra no puede estar vacía.");
            }
        }
        return palabra;
    }

    //Llena el arreglo estático pidiendo una palabra por cada casilla.
    public static void llenarArreglo(String[] palabras) throws IOException {
        for (int i = 0; i < palabras.length; i++) {
            palabras[i] = leerPalabra("Digite la palabra " + (i + 1) + ": ");
        }
    }

    //Imprime todas las casillas del arreglo.
    public static void mostrarArreglo(String[] palabras) {
        for (int i = 0; i < palabras.length; i++) {
            System.out.printf("[%d] %s%n", i, palabras[i]);
        }
    }

    //Compara dos palabras ignorando mayúsculas y minúsculas.
    //Devuelve negativo si a va antes que b, cero si son iguales y positivo si a va después que b.
    //Todos los algoritmos usan este método para que el orden y la búsqueda siempre coincidan.
    public static int comparar(String a, String b) {
        return a.compareToIgnoreCase(b);
    }

    //Intercambia el contenido de dos casillas del arreglo.
    public static void intercambiar(String[] palabras, int i, int j) {
        String temp = palabras[i];
        palabras[i] = palabras[j];
        palabras[j] = temp;
    }

    //Ordenamiento por selección.
    //Complejidad de tiempo: O(n^2) en todos los casos (mejor, esperado y peor), porque siempre recorre el resto del arreglo.
    public static void ordenamientoSeleccion(String[] palabras) {
        int n = palabras.length;

        //El ciclo externo avanza una casilla por pasada; las casillas anteriores a i ya quedaron ordenadas.
        for (int i = 0; i < n - 1; i++) {
            //Se asume que la palabra menor es la de la casilla actual.
            int menor = i;

            //El ciclo interno recorre la porción restante buscando la palabra menor.
            for (int j = i + 1; j < n; j++) {
                if (comparar(palabras[j], palabras[menor]) < 0) {
                    menor = j;
                }
            }

            //Si la menor no estaba en la casilla actual, se intercambian ambas casillas.
            if (menor != i) {
                intercambiar(palabras, i, menor);
            }
        }
    }

    //Ordenamiento por inserción.
    //Complejidad de tiempo: O(n^2) en el peor caso y en el caso esperado; O(n) en el mejor caso (arreglo ya ordenado).
    public static void ordenamientoInsercion(String[] palabras) {
        int n = palabras.length;

        //La casilla 0 se considera ordenada; el ciclo externo toma cada palabra siguiente para insertarla.
        for (int i = 1; i < n; i++) {
            //Se guarda la palabra a insertar porque su casilla podría sobrescribirse al desplazar.
            String actual = palabras[i];
            int j = i - 1;

            //Se desplazan una casilla a la derecha todas las palabras mayores que la actual, de mayor a menor.
            while (j >= 0 && comparar(palabras[j], actual) > 0) {
                palabras[j + 1] = palabras[j];
                j--;
            }

            //Se inserta la palabra en el espacio que quedó libre.
            palabras[j + 1] = actual;
        }
    }

    //Ordenamiento por burbuja.
    //Complejidad de tiempo: O(n^2) en el peor caso y en el caso esperado; O(n) en el mejor caso (arreglo ya ordenado).
    public static void ordenamientoBurbuja(String[] palabras) {
        int n = palabras.length;
        boolean huboCambios = true;

        //Cada pasada deja la palabra mayor de la porción restante en su casilla final.
        for (int i = 0; i < n - 1 && huboCambios; i++) {
            huboCambios = false;

            //Se comparan parejas adyacentes y se corrige cada desorden local; la porción se reduce en cada pasada.
            for (int j = 0; j < n - 1 - i; j++) {
                if (comparar(palabras[j], palabras[j + 1]) > 0) {
                    intercambiar(palabras, j, j + 1);
                    huboCambios = true;
                }
            }
            //Si en una pasada completa no hubo cambios, el arreglo ya está ordenado y se detiene el ciclo.
        }
    }

    //Ordenamiento por mezcla (merge sort).
    //Complejidad de tiempo: O(n log n) en todos los casos. Usa memoria extra de O(n).
    public static void ordenamientoMezcla(String[] palabras, int izquierda, int derecha) {
        //Caso base: una porción de una sola casilla (o vacía) ya está ordenada.
        if (izquierda < derecha) {
            //Se divide la porción en dos mitades.
            int medio = (izquierda + derecha) / 2;

            //Se ordena cada mitad de forma recursiva.
            ordenamientoMezcla(palabras, izquierda, medio);
            ordenamientoMezcla(palabras, medio + 1, derecha);

            //Se mezclan las dos mitades ya ordenadas en una sola porción ordenada.
            mezclar(palabras, izquierda, medio, derecha);
        }
    }

    //Mezcla dos mitades ordenadas [izquierda..medio] y [medio+1..derecha] en una sola porción ordenada.
    public static void mezclar(String[] palabras, int izquierda, int medio, int derecha) {
        //Se copian las dos mitades en arreglos temporales para no perder datos al sobrescribir.
        int tamanoIzquierda = medio - izquierda + 1;
        int tamanoDerecha = derecha - medio;
        String[] mitadIzquierda = new String[tamanoIzquierda];
        String[] mitadDerecha = new String[tamanoDerecha];

        for (int i = 0; i < tamanoIzquierda; i++) {
            mitadIzquierda[i] = palabras[izquierda + i];
        }
        for (int j = 0; j < tamanoDerecha; j++) {
            mitadDerecha[j] = palabras[medio + 1 + j];
        }

        int i = 0;
        int j = 0;
        int k = izquierda;

        //Se toma siempre la menor entre las primeras palabras de cada mitad y se coloca en el arreglo original.
        while (i < tamanoIzquierda && j < tamanoDerecha) {
            if (comparar(mitadIzquierda[i], mitadDerecha[j]) <= 0) {
                palabras[k] = mitadIzquierda[i];
                i++;
            } else {
                palabras[k] = mitadDerecha[j];
                j++;
            }
            k++;
        }

        //Si sobraron palabras en alguna mitad, se copian tal cual porque ya están ordenadas.
        while (i < tamanoIzquierda) {
            palabras[k] = mitadIzquierda[i];
            i++;
            k++;
        }
        while (j < tamanoDerecha) {
            palabras[k] = mitadDerecha[j];
            j++;
            k++;
        }
    }

    //Ordenamiento rápido (quick sort).
    //Complejidad de tiempo: O(n log n) en el mejor caso y en el caso esperado; O(n^2) en el peor caso (pivotes muy desbalanceados).
    public static void ordenamientoRapido(String[] palabras, int bajo, int alto) {
        //Caso base: una porción de una sola casilla (o vacía) ya está ordenada.
        if (bajo < alto) {
            //Se particiona: el pivote queda en su posición final, con menores a su izquierda y mayores a su derecha.
            int posicionPivote = particionar(palabras, bajo, alto);

            //Se ordenan de forma recursiva las porciones a ambos lados del pivote.
            ordenamientoRapido(palabras, bajo, posicionPivote - 1);
            ordenamientoRapido(palabras, posicionPivote + 1, alto);
        }
    }

    //Coloca el pivote (la última casilla de la porción) en su posición final y devuelve esa posición.
    public static int particionar(String[] palabras, int bajo, int alto) {
        //Se elige como pivote la última palabra de la porción.
        String pivote = palabras[alto];
        int i = bajo - 1;

        //Cada palabra menor o igual al pivote se mueve hacia la zona izquierda de la porción.
        for (int j = bajo; j < alto; j++) {
            if (comparar(palabras[j], pivote) <= 0) {
                i++;
                intercambiar(palabras, i, j);
            }
        }

        //Se coloca el pivote justo después de la zona de menores.
        intercambiar(palabras, i + 1, alto);
        return i + 1;
    }

    //Búsqueda binaria; requiere que el arreglo esté ordenado. Devuelve la casilla donde está la palabra o -1 si no existe.
    //Complejidad de tiempo: O(log n) en el peor caso y en el caso esperado, porque en cada paso se descarta la mitad del arreglo.
    //En el mejor caso es O(1), cuando la palabra está justo en el primer pivote.
    public static int busquedaBinaria(String[] palabras, String palabraBuscada) {
        //Se define el intervalo de búsqueda con todo el arreglo.
        int limiteMenor = 0;
        int limiteMayor = palabras.length - 1;

        //Mientras el intervalo tenga al menos una casilla, se sigue buscando.
        while (limiteMenor <= limiteMayor) {
            //Se calcula el pivote en el centro del intervalo actual.
            int pivote = limiteMenor + (limiteMayor - limiteMenor) / 2;
            int resultado = comparar(palabras[pivote], palabraBuscada);

            if (resultado == 0) {
                //La palabra del pivote es la buscada: se retorna su casilla.
                return pivote;
            } else if (resultado < 0) {
                //El pivote es menor que la buscada: se descarta la mitad izquierda.
                limiteMenor = pivote + 1;
            } else {
                //El pivote es mayor que la buscada: se descarta la mitad derecha.
                limiteMayor = pivote - 1;
            }
        }

        //Los límites se cruzaron: la palabra no está en el arreglo.
        return -1;
    }

    //Muestra el menú de ordenamiento y devuelve la opción elegida (1 a 5).
    public static int mostrarMenu() throws IOException {
        int opcion = 0;

        while (opcion < 1 || opcion > 5) {
            System.out.println();
            System.out.println("===== Algoritmos de ordenamiento =====");
            System.out.println("1. Selección  - O(n^2)");
            System.out.println("2. Inserción  - O(n^2)");
            System.out.println("3. Burbuja    - O(n^2)");
            System.out.println("4. Mezcla     - O(n log n)");
            System.out.println("5. Rápido     - O(n log n) promedio, O(n^2) peor caso");
            opcion = leerEntero("Elija un algoritmo (1-5): ", 1);

            if (opcion > 5) {
                System.out.println("Opción inválida. Elija un número entre 1 y 5.");
            }
        }
        return opcion;
    }

    //Pregunta al usuario si desea realizar otra búsqueda; acepta solo "s" o "n".
    public static boolean deseaBuscar() throws IOException {
        String respuesta = "";

        while (!respuesta.equals("s") && !respuesta.equals("n")) {
            System.out.print("¿Desea buscar una palabra? (s/n): ");
            respuesta = entrada.readLine().trim().toLowerCase();
            if (!respuesta.equals("s") && !respuesta.equals("n")) {
                System.out.println("Responda únicamente con s o n.");
            }
        }
        return respuesta.equals("s");
    }

    public static void main(String[] args) throws IOException {
        //Se pide el tamaño del arreglo y se crea el arreglo estático de palabras.
        int tamano = leerEntero("Digite la cantidad de palabras que quiere almacenar: ", 1);
        String[] palabras = new String[tamano];

        //Se llena el arreglo con las palabras del usuario.
        llenarArreglo(palabras);

        //Se muestra el menú y se ejecuta el algoritmo elegido.
        int opcion = mostrarMenu();

        switch (opcion) {
            case 1:
                ordenamientoSeleccion(palabras);
                break;
            case 2:
                ordenamientoInsercion(palabras);
                break;
            case 3:
                ordenamientoBurbuja(palabras);
                break;
            case 4:
                ordenamientoMezcla(palabras, 0, palabras.length - 1);
                break;
            case 5:
                ordenamientoRapido(palabras, 0, palabras.length - 1);
                break;
        }

        System.out.println();
        System.out.println("Arreglo ordenado:");
        mostrarArreglo(palabras);

        //Con el arreglo ya ordenado se permite buscar palabras con búsqueda binaria.
        System.out.println();
        while (deseaBuscar()) {
            String palabraBuscada = leerPalabra("Digite la palabra que desea buscar: ");
            int posicion = busquedaBinaria(palabras, palabraBuscada);

            if (posicion >= 0) {
                System.out.println("La palabra se encontró en la casilla: " + posicion);
            } else {
                System.out.println("La palabra no se encontró en el arreglo.");
            }
        }

        System.out.println("Programa finalizado.");
    }
}
