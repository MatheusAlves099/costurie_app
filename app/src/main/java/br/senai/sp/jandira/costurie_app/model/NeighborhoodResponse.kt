package br.senai.sp.jandira.costurie_app.model

data class NeighborhoodResponse(
    val Id: Int,
    val Nome: String
): Comparable<NeighborhoodResponse> {
    override fun compareTo(other: NeighborhoodResponse): Int {
        return Nome.compareTo(other.Nome)
    }
}