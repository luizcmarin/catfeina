package com.marin.catfeina.ui.componentes.textoformatado // Ou onde preferir

interface TooltipHandler {
    fun mostrarTooltip(textoTooltip: String)
    // Poderia adicionar fun clearTooltip() aqui também se o renderer precisasse disso,
    // mas atualmente só o Snackbar effect na Screen o usa.
}