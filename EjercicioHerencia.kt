import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)

    println("========================================")
    println("PRUEBA DE CUENTA DE AHORROS")
    println("========================================\n")

    print("Ingrese el saldo inicial para la cuenta de ahorros: ")
    val saldoInicial = scanner.nextFloat()
    print("Ingrese la tasa de interés anual (%): ")
    val tasaAnual = scanner.nextFloat()

    val cuentaAhorros = CuentaAhorros(saldoInicial, tasaAnual)

    //Probando metodos

    print("\nIngrese el valor a consignar: ")
    cuentaAhorros.consignar(scanner.nextFloat())

    print("Ingrese el valor a retirar: ")
    cuentaAhorros.retirar(scanner.nextFloat())

    println("\nGenerando extracto mensual...")
    cuentaAhorros.extractoMensual()
    cuentaAhorros.imprimir()

    scanner.close()
}
