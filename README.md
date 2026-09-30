# Sistema-RPG

**Descrição:** gerenciador de campanhas e fichas de RPG de mesa em Java com Swing. Salva os dados em arquivos `.txt`. Permite criar usuários, campanhas, fichas, inventário e rolar dados.

**Como executar:** abra a pasta na IDE e rode a classe `Main`. Execute sempre a partir da raiz do projeto (as pastas `Usuarios`, `Campanhas` e `Fichas` são criadas ali). No primeiro acesso, clique em **Cadastrar**.

**Classes principais:**
- `Usuario`, `Campanha`
- `Ficha`, `Protagonista`, `NPC`
- `Inventario`, `Equipamento`, `Arma`, `Armadura`
- `Dado`, `Rolagem`
- Pacote `Telas` (interface Swing)

**Regras de negócio:**
- O mestre vê todas as fichas da campanha. O jogador vê só as que criou.
- O mestre cria NPCs e o jogador cria Protagonistas.
- O inventário tem limite de carga.
- Nomes não podem ter caracteres ilegais para arquivo.

**Checklist:**

| Item | Onde |
|---|---|
| Classes, objetos, atributos, métodos | `Dado`, `Ficha`, `Inventario` |
| Encapsulamento | Atributos `private` e `Dado.setFaces` com validação |
| Construtores e `this` | `Rolagem` (construtores encadeados) |
| Pacotes | `classes` e `Telas` |
| Herança, abstração, `super`, polimorfismo | `Ficha` → `NPC`/`Protagonista`; `TelaCadastroFicha` |
| Interfaces | ❌ não implementado |
| Enumerações | `TipoEquipamento` |
| Exceções | `Dado`, `TelaRolagem`, `Ficha.salvarFicha` |
| Swing | Pacote `Telas` e `Main` |
| `java.time` | `Rolagem` e `Ficha` (`LocalDateTime`) |
| Regras de negócio | `Campanha.carregarFichas`, `Inventario.addEquipamento`, `ValidadorNome` |
