package br.senai.sp.jandira.costurie_app.model

data class CityResponse(
    val Id: Int,
    val Nome: String
): Comparable<CityResponse> {
    override fun compareTo(other: CityResponse): Int {
        return Nome.compareTo(other.Nome)
    }
}
