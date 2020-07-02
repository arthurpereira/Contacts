var tamanhoTelaInicial = window.innerHeight;

window.onresize = function(event) {
    if ((window.innerHeight < tamanhoTelaInicial)
        && !($("#div-botao-nova-mesa").hasClass('visivel'))) {
        $(".div-rodape-botoes").addClass("oculto");
    } else {
        $(".div-rodape-botoes").removeClass("oculto");
    }
};

function mostrarFormNovoContato() {
    $('#div-editar-contato').addClass("display-none");
    $('#div-editar-contato').hide();
    $('#div-visualizacao').addClass("display-none");
    $('#div-visualizacao').hide();
    $('#div-contatos').addClass("display-none");
    $('#sidebar').removeClass("sidebar-open");
    $('#conteudo').removeClass("sidebar-open");
    $('#div-titulo-sidebar').removeClass("sidebar-open");
    $('#div-titulo-conteudo').removeClass("sidebar-open");
    $('#div-novo-contato').removeClass("display-none");
    $('#div-novo-contato').show();
}

function mostrarFormEditarContato() {
    $('#div-novo-contato').addClass("display-none");
    $('#div-novo-contato').hide();
    $('#div-visualizacao').addClass("display-none");
    $('#div-visualizacao').hide();
    $('#div-contatos').addClass("display-none");
    $('#sidebar').removeClass("sidebar-open");
    $('#conteudo').removeClass("sidebar-open");
    $('#div-titulo-sidebar').removeClass("sidebar-open");
    $('#div-titulo-conteudo').removeClass("sidebar-open");
    $('#div-editar-contato').removeClass("display-none");
    $('#div-editar-contato').show();
}

function mostrarListaDeContatos() {
    $('#div-editar-contato').addClass("display-none");
    $('#div-editar-contato').hide();
    $('#div-novo-contato').addClass("display-none");
    $('#div-novo-contato').hide();
    $('#div-visualizacao').addClass("display-none");
    $('#div-visualizacao').hide();
    $('#conteudo').addClass("sidebar-open");
    $('#sidebar').addClass("sidebar-open");
    $('#div-titulo-conteudo').addClass("sidebar-open");
    $('#div-titulo-sidebar').addClass("sidebar-open");
    $('#div-contatos').removeClass("display-none");
}

function mostrarViewContato() {
    $('#div-editar-contato').addClass("display-none");
    $('#div-editar-contato').hide();
    $('#div-novo-contato').addClass("display-none");
    $('#div-novo-contato').hide();
    $('#div-contatos').addClass("display-none");
    $('#sidebar').removeClass("sidebar-open");
    $('#conteudo').removeClass("sidebar-open");
    $('#div-titulo-sidebar').removeClass("sidebar-open");
    $('#div-titulo-conteudo').removeClass("sidebar-open");
    $('#div-visualizacao').removeClass("display-none");
    $('#div-visualizacao').show();
}

// Lógica campos de texto
$(document).on('focus', 'input[type=text], input[type=tel], input[type=email]', function() {
    $('.ui-input-clear').remove();
});

$(document).on('blur', 'input[type=text], input[type=tel], input[type=email]', function() {
    $('.ui-input-clear').remove();
});