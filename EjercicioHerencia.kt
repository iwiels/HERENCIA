import java.util.Scanner

// Clase Padre o Superclase
open class Cuenta(protected var saldo: Float, protected var tasaAnual: Float) {
    protected var numeroConsignaciones = 0
    protected var numeroRetiros = 0
    protected var comisionMensual = 0f

    // Constructor
    init {
        println("Se ha creado una nueva cuenta con saldo inicial de $$saldo y tasa anual de $tasaAnual%.")
    }

    // Métodos
    open fun consignar(cantidad: Float) {
        if (cantidad > 0) {
            saldo += cantidad
            numeroConsignaciones++
            println("Consignación exitosa por $$cantidad. Nuevo saldo: $$saldo.")
        } else {
            println("La cantidad a consignar debe ser mayor a cero.")
        }
    }

    open fun retirar(cantidad: Float) {
        if (cantidad > 0 && cantidad <= saldo) {
            saldo -= cantidad
            numeroRetiros++
            println("Retiro exitoso por $$cantidad. Nuevo saldo: $$saldo.")
        } else if (cantidad > saldo) {
            println("Fondos insuficientes para retirar $$cantidad. Saldo actual: $$saldo.")
        } else {
            println("La cantidad a retirar debe ser mayor a cero.")
        }
    }

    fun calcularInteresMensual() {
        val interesMensual = saldo * (tasaAnual / 12) / 100
        saldo += interesMensual
        println("Interés mensual calculado: $$interesMensual. Nuevo saldo: $$saldo.")
    }

    open fun extractoMensual() {
        saldo -= comisionMensual
        calcularInteresMensual()
        println("Extracto mensual generado. Comisión: $$comisionMensual.")
    }

    open fun imprimir() {
        println("---------------------------------")
        println("Resumen de la Cuenta:")
        println("Saldo: $$saldo")
        println("Comisión Mensual: $$comisionMensual")
        println("Número de transacciones realizadas: ${numeroConsignaciones + numeroRetiros}")
        println("---------------------------------")
    }
}

// Subclase o Clase Hija
class CuentaAhorros(saldo: Float, tasaAnual: Float) : Cuenta(saldo, tasaAnual) {
    private var activa: Boolean = saldo >= 10000

    init {
        println("Cuenta de Ahorros ${if (activa) "Activada" else "Inactiva"}.")
    }

    override fun consignar(cantidad: Float) {
        if (activa) {
            super.consignar(cantidad)
        } else {
            println("No se puede consignar dinero, la cuenta está inactiva.")
        }
    }

    override fun retirar(cantidad: Float) {
        if (activa) {
            super.retirar(cantidad)
        } else {
            println("No se puede retirar dinero, la cuenta está inactiva.")
        }
    }

    override fun extractoMensual() {
        if (numeroRetiros > 4) {
            comisionMensual += (numeroRetiros - 4) * 1000f
            println("Se ha aplicado una comisión de $${(numeroRetiros - 4) * 1000} por exceder 4 retiros.")
        }
        super.extractoMensual()
        activa = saldo >= 10000
        println("Estado de la cuenta después del extracto: ${if (activa) "Activa" else "Inactiva"}.")
    }

    override fun imprimir() {
        println("---------------------------------")
        println("Resumen de la Cuenta de Ahorros:")
        println("Saldo: $$saldo")
        println("Comisión Mensual: $$comisionMensual")
        println("Número de transacciones realizadas: ${numeroConsignaciones + numeroRetiros}")
        println("Estado de la cuenta: ${if (activa) "Activa" else "Inactiva"}")
        println("---------------------------------")
    }
}

// Subclase o Clase Hija
class CuentaCorriente(saldo: Float, tasaAnual: Float) : Cuenta(saldo, tasaAnual) {
    var sobregiro: Float = 0f

    override fun retirar(cantidad: Float) {
        val disponible = saldo + sobregiro
        if (cantidad > disponible) {
            println("No es posible retirar la cantidad. Excede el sobregiro permitido.")
        } else if (cantidad > saldo) {
            sobregiro -= (cantidad - saldo)
            saldo = 0f
            numeroRetiros++
            println("Retiro exitoso. Saldo actual: $0. Monto en sobregiro: $$sobregiro.")
        } else {
            super.retirar(cantidad)
        }
    }

    override fun consignar(cantidad: Float) {
        if (sobregiro < 0) {
            val abonoASobregiro = minOf(cantidad, -sobregiro)
            sobregiro += abonoASobregiro
            val restoConsignacion = cantidad - abonoASobregiro
            if (restoConsignacion > 0) {
                super.consignar(restoConsignacion)
            } else {
                 numeroConsignaciones++
                 println("Abono a sobregiro por $$cantidad. Nuevo sobregiro: $$sobregiro")
            }
        } else {
            super.consignar(cantidad)
        }
    }

    override fun imprimir() {
        println("---------------------------------")
        println("Resumen de la Cuenta Corriente:")
        println("Saldo: $$saldo")
        println("Comisión Mensual: $$comisionMensual")
        println("Número de transacciones realizadas: ${numeroConsignaciones + numeroRetiros}")
        println("Valor de sobregiro: $$sobregiro")
        println("---------------------------------")
    }
}

// Método main para probar la funcionalidad
fun main() {
    val scanner = Scanner(System.`in`)
    println("Bienvenido al sistema bancario.")
    print("Ingrese el saldo inicial para la cuenta de ahorros: ")
    val saldoInicial = scanner.nextFloat()
    print("Ingrese la tasa de interés anual (%): ")
    val tasaAnual = scanner.nextFloat()

    val cuentaAhorros = CuentaAhorros(saldoInicial, tasaAnual)
    cuentaAhorros.imprimir()

    // Pruebas de operaciones
    print("\nIngrese el valor a consignar: ")
    cuentaAhorros.consignar(scanner.nextFloat())

    print("Ingrese el valor a retirar: ")
    cuentaAhorros.retirar(scanner.nextFloat())

    // Generar extracto mensual
    println("\nGenerando extracto mensual...")
    cuentaAhorros.extractoMensual()
    cuentaAhorros.imprimir()

    scanner.close()
}
