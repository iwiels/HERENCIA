open class Cuenta(protected var saldo: Float, protected var tasaAnual: Float) {
    protected var numeroConsignaciones = 0
    protected var numeroRetiros = 0
    protected var comisionMensual = 0f

    init {
        println("Se ha creado una nueva cuenta:\n" +
                "Saldo inicial: $$saldo\n" +
                "Tasa anual: $tasaAnual%")
    }

    open fun consignar(cantidad: Float) {
        if (cantidad > 0) {
            saldo += cantidad
            numeroConsignaciones++
            println("Consignacion exitosa por $$cantidad\n" +
                    "Nuevo saldo: $$saldo")
        } else {
            println("La cantidad a consignar debe ser mayor a cero")
        }
    }

    open fun retirar(cantidad: Float) {
        if (cantidad > 0 && cantidad <= saldo) {
            saldo -= cantidad
            numeroRetiros++
            println("Retiro exitoso por $$cantidad\n" +
                    "Nuevo saldo: $$saldo")
        } else if (cantidad > saldo) {
            println("Fondos insuficientes para retirar $$cantidad")
        } else {
            println("La cantidad a retirar debe ser mayor a cero")
        }
    }

    private fun calcularInteresMensual() {
        val interesMensual = saldo * (tasaAnual / 100) / 12
        saldo += interesMensual
        println("Interes mensual calculado: $$interesMensual\n" +
                "Nuevo saldo: $$saldo")
    }

    open fun extractoMensual() {
        saldo -= comisionMensual
        println("Comision mensual: $$comisionMensual.")
        calcularInteresMensual()
    }

    open fun imprimir() {
        println("---------------------------------")
        println("Resumen de la cuenta:")
        println("Saldo: $$saldo")
        println("Comision Mensual: $$comisionMensual")
        println("Tasa anual: $$tasaAnual")
        println("Consignaciones realizadas: $numeroConsignaciones")
        println("Retiros realizados: $numeroRetiros")
        println("---------------------------------")
    }
}