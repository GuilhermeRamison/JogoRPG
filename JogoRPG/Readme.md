(Versão inacabada)
Jogo feito para colocar em prática os conceitos da POO.


>>Para executar o jogo:

Abra o diretório contendo o JogoRPG.jar e os demais arquivos .txt no terminal. Digite:
java -jar "JogoRPG.jar"

>>Intruções do jogo

Enquanto fora de batalha:
go <direção> 		 (vai para a próxima sala do jogo) EXEMPLO: go north
heal <item>   		 (irá curar o personagem com o item escolhido) EXEMPLO: heal Fruta
talk <pessoa> 		 (irá interagir com o NPC escolhido. No caso de mercadores e armeiros, ira abrir a loja também) EXEMPLO: talk Fergus
drop <item>   		 (irá largar o item escolhido do inventário) EXEMPLO: drop Adaga 
pick <item>   		 (irá pegar o item escolhido da sala) EXEMPLO: pick Adaga
equip <item> 		 (irá equipar e ativar os bonus do item escolhido do inventário) EXEMPLO: equip Adaga
unequip <tipo do item>   (irá desequipar e retirar os bonus do item escolhido do inventário) EXEMPLO: unequip weapon (weapon é o tipo de Adaga)
sell <item>   		 (vende o item escolhido do inventário, apenas quando perto de um mercador ou armeiro) EXEMPLO: sell Adaga
buy <item>		 (compra o item escolhido na loja, apenas quando perto de um mercador ou armeiro) EXEMPLO: buy Adaga
repair <item>		 (repara o item escolhido do inventário, apenas quando perto de um armeiro) EXEMPLO: repair Adaga
inventory		 (mostra seu Status e seu Inventário)
quit                     (encerra o jogo)

Enquanto dentro de batalha:
attack <inimigo>         (ataca o inimigo indicado) EXEMPLO: attack Kobold
heal <item>   		 (irá curar o personagem com o item escolhido) EXEMPLO: heal Fruta
go <direção> 		 (semelhante quando fora de batalha, porém você só pode ir para a sala anterior) 
quit                     (encerra o jogo)
