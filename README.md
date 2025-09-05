# Monitor de pastas e envio de email

Esse projeto foi uma iniciativa própria de automação de tarefa de envio de emails que são realizados todos os meses contendo, o arquivo de SPED fiscal que são validados e posteriormente enviados para a contabilidade. A aplicação consiste em monitorar uma pasta raiz que pode conter ou não várias outras dentro delas, e sempre que um arquivo é adicionado como sufixo _VALIDADO ou _RETIFICADO, a aplicação imediatamente aciona o serviço de email, que pega todas as informações referentes ao cliente e o email da contabilidade, assim como o arquivo de SPED que será enviado.

Atualmente, para que funcione como esperado as pastas devem seguir uma certa estrutura, contendo o nome do cliente e email da contabilidade. Segue a estrutura abaixo:
NOME_CLIENTE (EMAIL_CONTABILIDADE)

