(Versão inacabada)
Jogo feito para colocar em prática os conceitos da POO.


>>Para executar o jogo:

Abra o diretório contendo o JogoRPG.jar e os demais arquivos .txt no terminal. Digite:
java -jar "JogoRPG.jar"

>>Intruções do jogo

Enquanto fora de batalha:\n
go <direção> 		 (vai para a próxima sala do jogo) EXEMPLO: go north\n
heal <item>   		 (irá curar o personagem com o item escolhido) EXEMPLO: heal Fruta\n
talk <pessoa> 		 (irá interagir com o NPC escolhido. No caso de mercadores e armeiros, ira abrir a loja também) EXEMPLO: talk Fergus\n
drop <item>   		 (irá largar o item escolhido do inventário) EXEMPLO: drop Adaga \n
pick <item>   		 (irá pegar o item escolhido da sala) EXEMPLO: pick Adaga\n
equip <item> 		 (irá equipar e ativar os bonus do item escolhido do inventário) EXEMPLO: equip Adaga\n
unequip <tipo do item>   (irá desequipar e retirar os bonus do item escolhido do inventário) EXEMPLO: unequip weapon (weapon é o tipo de Adaga)\n
sell <item>   		 (vende o item escolhido do inventário, apenas quando perto de um mercador ou armeiro) EXEMPLO: sell Adaga\n
buy <item>		 (compra o item escolhido na loja, apenas quando perto de um mercador ou armeiro) EXEMPLO: buy Adaga\n
repair <item>		 (repara o item escolhido do inventário, apenas quando perto de um armeiro) EXEMPLO: repair Adaga\n
inventory		 (mostra seu Status e seu Inventário)\n
quit                     (encerra o jogo)\n

Enquanto dentro de batalha:\n
attack <inimigo>         (ataca o inimigo indicado) EXEMPLO: attack Kobold\n
heal <item>   		 (irá curar o personagem com o item escolhido) EXEMPLO: heal Fruta\n
go <direção> 		 (semelhante quando fora de batalha, porém você só pode ir para a sala anterior) \n
quit                     (encerra o jogo)\n
