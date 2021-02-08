# B2Wpratico

<h2>Working Flow<h2/>

Rota POST - /planetas "Cadastra um planeta, gerando o seu id e pesquisando pelo nome cadastrado se existe na api do Star Wars para saber a quantidade a aparições."

```
{
    "nome": "Kamino",
    "clima": "Quente",
    "terreno": "Planice"
}
```

Rota GET - /planetas "Retorna a lista de todos os planetas cadastrados"

Rota GET - /planetas/nome/{nome} "Retorna um planeta pesquisando pelo nome"

Rota GET - /planetas/{id} "Retorna um planeta pesquisando pelo id"

Rota DELETE - /planetas/{id} "Deleta um planeta pelo id"
