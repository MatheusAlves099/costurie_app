package br.senai.sp.jandira.costurie_app.model

data class StateResponse(
    val Nome: String
): Comparable<StateResponse> {
    override fun compareTo(other: StateResponse): Int {
        return Nome.compareTo(other.Nome)
    }
}
