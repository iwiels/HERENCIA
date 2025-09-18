class CuentaAhorros(saldo: Float, tasaAnual: Float) : Cuenta(saldo, tasaAnual) {
    private var activa: Boolean = saldo >= 10000

    init {
        println("Cuenta de ahorros creada")
    }

    override fun consignar(cantidad: Float) {
        if (activa) {
            super.consignar(cantidad)
        } else {
            println("La cuenta esta inactiva no se puede consignar dinero")
        }
    }

    override fun retirar(cantidad: Float) {
        if (activa) {
            super.retirar(cantidad)
        } else {
            println("La cuenta esta inactiva no se puede retirar dinero")
        }
    }

    override fun extractoMensual() {
        if (numeroRetiros > 4) {
            comisionMensual += (numeroRetiros - 4) * 1000f
            println("Se ha aplicado una comision de $${(numeroRetiros - 4) * 1000} por exceder 4 retiros")
        }
        super.extractoMensual()
        activa = saldo >= 10000
        println("Estado de la cuenta: ${if (activa) "Activa" else "Inactiva"}")
    }

    override fun imprimir() {
        println("---------------------------------")
        println("Cuenta de Ahorros:")
        println("Saldo: $$saldo")
        println("Comision Mensual: $$comisionMensual")
        println("Transacciones realizadas: ${numeroConsignaciones + numeroRetiros}")
        println("---------------------------------")
    }
}