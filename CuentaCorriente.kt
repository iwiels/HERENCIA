class CuentaCorriente(saldo: Float, tasaAnual: Float) : Cuenta(saldo, tasaAnual) {
    private var sobregiro: Float = 0f

    override fun retirar(cantidad: Float) {
        if (cantidad > saldo) {
            sobregiro -= (cantidad - saldo)
            saldo = 0f
            numeroRetiros++
            println("Retiro exitoso\n" +
                    "Saldo actual: $0\n" +
                    "Monto en sobregiro: $$sobregiro")
        } else {
            super.retirar(cantidad)
        }
    }

    override fun consignar(cantidad: Float) {
        if (sobregiro < 0) {
            val montoSobregiro = -sobregiro
            if (cantidad <= montoSobregiro) {
                sobregiro += cantidad
                numeroConsignaciones++
                println("Abono a sobregiro por $$cantidad")
            } else {
                val dineroParaSaldo = cantidad - montoSobregiro
                sobregiro = 0f
                super.consignar(dineroParaSaldo)
            }
        } else {
            super.consignar(cantidad)
        }
    }

    override fun extractoMensual() {
        super.extractoMensual()
    }

    override fun imprimir() {
        println("---------------------------------")
        println("Cuenta Corriente:")
        println("Saldo: $$saldo")
        println("Comision Mensual: $$comisionMensual")
        println("Numero de transacciones realizadas: ${numeroConsignaciones + numeroRetiros}")
        println("Valor de sobregiro: $$sobregiro")
        println("---------------------------------")
    }
}
