package ALURA.Literalura;

import ALURA.Literalura.Service.ConvierteDatos;
import ALURA.Literalura.model.DatosLibros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private ConvierteDatos convierteDatos;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		exhibirMenuPrincipal();
	}

	private void exhibirMenuPrincipal() {
		Scanner scanner = new Scanner(System.in);
		boolean ejecutando = true;

		System.out.println("\n" + "=".repeat(50));
		System.out.println(" BIENVENIDO A LITERALURA - BIBLIOTECA DIGITAL 📚");
		System.out.println("=".repeat(50) + "\n");

		while (ejecutando) {

			mostrarOpcionesMenu();

			try {

				System.out.print("\nIngrese el número de opción: ");
				int opcion = Integer.parseInt(scanner.nextLine());

				switch (opcion) {
					case 1 -> ejecutarConsultaLibros(scanner);
					case 2 -> ejecutarBusquedaTitulo(scanner);
					case 3 -> ejecutarBusquedaAutor(scanner);
					case 4 -> ejecutarFiltroIdioma(scanner);
					case 5 -> ejecutarEstadisticas();
					case 6 -> ejecutarTopDescargados();
					case 7 -> ejecutarRegistroLibro(scanner);
					case 8 -> ejecutarConsultaRegistrados();
					case 9 -> mostrarAyuda();
					case 0 -> ejecutando = confirmarSalida(scanner);
					default -> mostrarError("Opción no válida. Debe ser un número entre 0 y 9.");
				}

			} catch (NumberFormatException e) {
				mostrarError("Debe ingresar un número válido.");
			} catch (Exception e) {
				mostrarError("Error inesperado: " + e.getMessage());
			}

			if (ejecutando) {
				System.out.print("\nPresione Enter para continuar...");
				scanner.nextLine();
			}
		}

		scanner.close();
		System.out.println("\n ¡Gracias por usar Literalura! Hasta pronto.\n");
	}

	private void mostrarOpcionesMenu() {
		System.out.println("\n" + "=".repeat(40));
		System.out.println("MENÚ PRINCIPAL - LITERALURA");
		System.out.println("=".repeat(40));
		System.out.println("\n=== CONSULTAS ===");
		System.out.println("1.  Consultar libros por página");
		System.out.println("2.  Buscar libros por título");
		System.out.println("3.  Buscar libros por autor");
		System.out.println("4.  Filtrar libros por idioma");
		System.out.println("5.  Ver estadísticas");
		System.out.println("6.  Top libros más descargados");

		System.out.println("\n=== REGISTROS ===");
		System.out.println("7.  Registrar nuevo libro");
		System.out.println("8.  Ver libros registrados");

		System.out.println("\n=== OTROS ===");
		System.out.println("9.  Ayuda");
		System.out.println("0.  Salir");
		System.out.println("=".repeat(40));
	}

	private void ejecutarConsultaLibros(Scanner scanner) {
		System.out.println("\n" + "=".repeat(40));
		System.out.println("CONSULTAR LIBROS POR PÁGINA");
		System.out.println("=".repeat(40));

		System.out.print("Ingrese el número de página (1-100): ");
		try {
			int pagina = Integer.parseInt(scanner.nextLine());

			if (pagina < 1 || pagina > 100) {
				mostrarError("El número de página debe estar entre 1 y 100.");
				return;
			}

			System.out.println("\n⏳ Cargando libros de la página " + pagina + "...");
			List<DatosLibros> libros = convierteDatos.obtenerLibrosDePagina(pagina);

			if (libros.isEmpty()) {
				System.out.println(" No se encontraron libros en la página " + pagina);
				return;
			}

			System.out.println("\n RESULTADOS - Página " + pagina);
			System.out.println("Total de libros encontrados: " + libros.size());
			System.out.println("-".repeat(50));

			for (int i = 0; i < Math.min(libros.size(), 5); i++) {
				DatosLibros libro = libros.get(i);
				System.out.println((i + 1) + ". " + libro.titulo());
				System.out.println("   Autor: " + libro.getAutoresComoString());
				System.out.println("   Descargas: " + libro.descargas());
				System.out.println("-".repeat(50));
			}

			if (libros.size() > 5) {
				System.out.println("... y " + (libros.size() - 5) + " libros más");
			}

		} catch (NumberFormatException e) {
			mostrarError("Debe ingresar un número válido para la página.");
		} catch (Exception e) {
			mostrarError("Error al consultar libros: " + e.getMessage());
		}
	}

	private void ejecutarBusquedaTitulo(Scanner scanner) {
		System.out.println("\n" + "=".repeat(40));
		System.out.println("BUSCAR LIBROS POR TÍTULO");
		System.out.println("=".repeat(40));

		System.out.print("Ingrese el título a buscar: ");
		String titulo = scanner.nextLine().trim();

		if (titulo.isEmpty()) {
			mostrarError("Debe ingresar un título para buscar.");
			return;
		}

		System.out.println("\n Buscando: \"" + titulo + "\"...");

		try {
			List<DatosLibros> libros = convierteDatos.buscarLibrosPorTitulo(titulo);

			if (libros.isEmpty()) {
				System.out.println(" No se encontraron libros con el título: \"" + titulo + "\"");
				return;
			}

			System.out.println("\nRESULTADOS DE BÚSQUEDA");
			System.out.println("Libros encontrados: " + libros.size());
			System.out.println("-".repeat(50));

			libros.forEach(libro -> {
				System.out.println("• Título: " + libro.titulo());
				System.out.println("  Autor(es): " + libro.getAutoresComoString());
				System.out.println("  Idiomas: " + libro.getIdiomasComoString());
				System.out.println("  Descargas: " + libro.descargas());
				System.out.println("-".repeat(50));
			});

		} catch (Exception e) {
			mostrarError("Error en la búsqueda: " + e.getMessage());
		}
	}

	private void ejecutarBusquedaAutor(Scanner scanner) {
		System.out.println("\n" + "=".repeat(40));
		System.out.println("BUSCAR LIBROS POR AUTOR");
		System.out.println("=".repeat(40));

		System.out.print("Ingrese el nombre del autor: ");
		String autor = scanner.nextLine().trim();

		if (autor.isEmpty()) {
			mostrarError("Debe ingresar un nombre de autor.");
			return;
		}

		System.out.println("\n  Implementación en desarrollo...");
		System.out.println("Por ahora, mostrando libros de la página 1 que contengan autores:");

		try {
			List<DatosLibros> libros = convierteDatos.obtenerLibrosDePagina(1);
			List<DatosLibros> filtrados = libros.stream()
					.filter(libro -> libro.getAutoresComoString().toLowerCase().contains(autor.toLowerCase()))
					.toList();

			if (filtrados.isEmpty()) {
				System.out.println("No se encontraron libros del autor: " + autor);
				return;
			}

			System.out.println("\nLibros relacionados con \"" + autor + "\":");
			System.out.println("-".repeat(50));

			filtrados
					.forEach(libro -> System.out.println("• " + libro.titulo() + " - " + libro.getAutoresComoString()));

		} catch (Exception e) {
			mostrarError("Error en la búsqueda: " + e.getMessage());
		}
	}

	private void ejecutarFiltroIdioma(Scanner scanner) {
		System.out.println("\n" + "=".repeat(40));
		System.out.println("FILTRAR LIBROS POR IDIOMA");
		System.out.println("=".repeat(40));

		System.out.println("Seleccione el idioma:");
		System.out.println("1. Inglés (en)");
		System.out.println("2. Español (es)");
		System.out.println("3. Francés (fr)");
		System.out.println("4. Portugués (pt)");
		System.out.print("\nOpción: ");

		try {
			int opcion = Integer.parseInt(scanner.nextLine());
			String idioma = switch (opcion) {
				case 1 -> "en";
				case 2 -> "es";
				case 3 -> "fr";
				case 4 -> "pt";
				default -> {
					mostrarError("Opción no válida. Usando inglés por defecto.");
					yield "en";
				}
			};

			System.out.print("Ingrese número de página (1-100): ");
			int pagina = Integer.parseInt(scanner.nextLine());

			if (pagina < 1 || pagina > 100) {
				mostrarError("Página debe estar entre 1 y 100.");
				return;
			}

			System.out.println("\n Buscando libros en " + obtenerNombreIdioma(idioma) + "...");

			// Implementación básica - filtrar de la página
			List<DatosLibros> libros = convierteDatos.obtenerLibrosDePagina(pagina);
			List<DatosLibros> filtrados = libros.stream()
					.filter(libro -> libro.idiomas().contains(idioma))
					.toList();

			if (filtrados.isEmpty()) {
				System.out.println("No se encontraron libros en " + obtenerNombreIdioma(idioma));
				return;
			}

			System.out.println("\n Libros en " + obtenerNombreIdioma(idioma) + ":");
			System.out.println("-".repeat(50));

			filtrados.forEach(
					libro -> System.out.println("• " + libro.titulo() + " (" + libro.descargas() + " descargas)"));

		} catch (NumberFormatException e) {
			mostrarError("Debe ingresar un número válido.");
		} catch (Exception e) {
			mostrarError("Error al filtrar: " + e.getMessage());
		}
	}

	private void ejecutarEstadisticas() {
		System.out.println("\n" + "=".repeat(40));
		System.out.println("ESTADÍSTICAS GENERALES");
		System.out.println("=".repeat(40));

		try {
			List<DatosLibros> libros = convierteDatos.obtenerLibrosDePagina(1);

			if (libros.isEmpty()) {
				System.out.println("No hay datos para estadísticas.");
				return;
			}

			// Cálculos
			long totalDescargas = libros.stream()
					.mapToLong(libro -> libro.descargas() != null ? libro.descargas() : 0)
					.sum();

			double promedio = libros.stream()
					.mapToDouble(libro -> libro.descargas() != null ? libro.descargas() : 0)
					.average()
					.orElse(0);

			long enEspanol = libros.stream()
					.filter(libro -> libro.idiomas().contains("es"))
					.count();

			// Exhibir información
			System.out.println("\n ESTADÍSTICAS PÁGINA 1:");
			System.out.println("-".repeat(30));
			System.out.println("Total libros: " + libros.size());
			System.out.println("Total descargas: " + totalDescargas);
			System.out.printf("Promedio descargas: %.1f%n", promedio);
			System.out.println("Libros en español: " + enEspanol);
			System.out.printf("Porcentaje español: %.1f%%%n", (enEspanol * 100.0) / libros.size());

		} catch (Exception e) {
			mostrarError("Error al calcular estadísticas: " + e.getMessage());
		}
	}

	private void ejecutarTopDescargados() {
		System.out.println("\n" + "=".repeat(40));
		System.out.println("TOP LIBROS MÁS DESCARGADOS");
		System.out.println("=".repeat(40));

		try {
			List<DatosLibros> libros = convierteDatos.obtenerLibrosDePagina(1);

			if (libros.isEmpty()) {
				System.out.println("No hay libros para ranking.");
				return;
			}

			List<DatosLibros> top = libros.stream()
					.sorted((l1, l2) -> {
						int d1 = l1.descargas() != null ? l1.descargas() : 0;
						int d2 = l2.descargas() != null ? l2.descargas() : 0;
						return Integer.compare(d2, d1);
					})
					.limit(10)
					.toList();

			System.out.println("\n TOP 10 MÁS DESCARGADOS:");
			System.out.println("=".repeat(60));

			for (int i = 0; i < top.size(); i++) {
				DatosLibros libro = top.get(i);
				System.out.printf("%2d. %-50s%n", (i + 1),
						truncarTexto(libro.titulo(), 50));
				System.out.printf("    Autor: %s%n", libro.getAutoresComoString());
				System.out.printf("    Descargas: %,d%n", libro.descargas());
				System.out.println("-".repeat(60));
			}

		} catch (Exception e) {
			mostrarError("Error al generar ranking: " + e.getMessage());
		}
	}

	private void ejecutarRegistroLibro(Scanner scanner) {
		System.out.println("\n" + "=".repeat(40));
		System.out.println("REGISTRAR NUEVO LIBRO");
		System.out.println("=".repeat(40));

		System.out.println("\n INGRESE LOS DATOS DEL LIBRO:");

		try {
			System.out.print("Título: ");
			String titulo = scanner.nextLine().trim();

			if (titulo.isEmpty()) {
				mostrarError("El título no puede estar vacío.");
				return;
			}

			System.out.print("Autor(es) (separados por coma): ");
			String autores = scanner.nextLine().trim();

			System.out.print("Idioma(s) (ej: es,en,fr): ");
			String idiomas = scanner.nextLine().trim();

			System.out.print("Número de descargas: ");
			int descargas = Integer.parseInt(scanner.nextLine());

			// Aquí normalmente guardarías en una base de datos
			// Por ahora solo mostramos confirmación
			System.out.println("\n LIBRO REGISTRADO EXITOSAMENTE");
			System.out.println("-".repeat(30));
			System.out.println("Título: " + titulo);
			System.out.println("Autor(es): " + autores);
			System.out.println("Idioma(s): " + idiomas);
			System.out.println("Descargas: " + descargas);
			System.out.println("-".repeat(30));
			System.out.println("\n Nota: En una versión real, estos datos");
			System.out.println("se guardarían en una base de datos.");

		} catch (NumberFormatException e) {
			mostrarError("Las descargas deben ser un número válido.");
		} catch (Exception e) {
			mostrarError("Error al registrar libro: " + e.getMessage());
		}
	}

	private void ejecutarConsultaRegistrados() {
		System.out.println("\n" + "=".repeat(40));
		System.out.println("LIBROS REGISTRADOS");
		System.out.println("=".repeat(40));

		System.out.println("\n FUNCIONALIDAD EN DESARROLLO");
		System.out.println("Esta opción mostraría los libros que has");
		System.out.println("registrado en la base de datos local.");
		System.out.println("\nActualmente no hay libros registrados.");
	}

	private void mostrarAyuda() {
		System.out.println("\n" + "=".repeat(40));
		System.out.println("AYUDA - LITERALURA");
		System.out.println("=".repeat(40));

		System.out.println("\nINSTRUCCIONES DE USO:");
		System.out.println("1. Seleccione una opción numérica del menú");
		System.out.println("2. Siga las instrucciones en pantalla");
		System.out.println("3. Ingrese los datos solicitados");
		System.out.println("4. Revise los resultados mostrados");

		System.out.println("\n OPCIONES DISPONIBLES:");
		System.out.println("• Opciones 1-6: Consultas a la API Gutendex");
		System.out.println("• Opciones 7-8: Registro local de libros");
		System.out.println("• Opción 9: Esta ayuda");
		System.out.println("• Opción 0: Salir del programa");

		System.out.println("\n CONSEJOS:");
		System.out.println("• Use números enteros para seleccionar opciones");
		System.out.println("• Asegúrese de tener conexión a internet");
		System.out.println("• Los datos se obtienen en tiempo real");
	}

	private boolean confirmarSalida(Scanner scanner) {
		System.out.println("\n" + "=".repeat(40));
		System.out.println("CONFIRMAR SALIDA");
		System.out.println("=".repeat(40));

		System.out.print("¿Está seguro que desea salir? (S/N): ");
		String respuesta = scanner.nextLine().trim().toUpperCase();

		return respuesta.equals("S") || respuesta.equals("SI");
	}

	private void mostrarError(String mensaje) {
		System.out.println("\n  ERROR: " + mensaje);
	}

	private String obtenerNombreIdioma(String codigo) {
		return switch (codigo) {
			case "en" -> "Inglés";
			case "es" -> "Español";
			case "fr" -> "Francés";
			case "pt" -> "Portugués";
			default -> "Desconocido";
		};
	}

	private String truncarTexto(String texto, int longitud) {
		if (texto == null)
			return "";
		return texto.length() <= longitud ? texto : texto.substring(0, longitud - 3) + "...";
	}
}
