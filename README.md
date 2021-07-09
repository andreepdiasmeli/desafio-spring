# Desafio Spring - Social Meli

Neste repositório está a implementação do **Desafio Spring** do módulo sobre Spring Boot do bootcamp da Digital House realizado no Mercado Livre:yellow_heart::blue_heart:.

A aplicação desenvolvida consiste em uma API REST que permite que compradores sigam vendedores que podem realizar publicações de novos produtos na plataforma. O sistema desenvolvido é denominado **SocialMeli** :rocket:.


## Tecnologias utilizadas

Para construir a plataforma foi utilizado o framework **Spring Boot** para a linguagem Java. A persistência de dados foi realizada com a implementação do JPA **Hibernate**, com o banco de dados relacional em memória **H2**. Também foi utilizado o **Bean Validation** para a validação de dados recebidos pelos endpoints. O sistema conta com a documentação gerada pelo **Swagger**.


## Arquitetura

A aplicação foi construída em camadas, onde existe um pacote **controllers** para receber as requisições REST, um pacote de **services** para implementar a lógica do negócio e um pacote **repositories** para a manipulação dos dados. As entidades da aplicação que refletem o modelo relacional se encontram no pacote **entities**. Existe um *handler* para tratar diferentes tipos de exceções e um pacote **exceptions** onde ficam as exceções personalizadas. A transferência de dados externos a aplicação acontecem com objetos das classes do pacote **dtos**.

O diagrama abaixo representa o relacionamento entre as entidades e sua cardinalidade:

<p align="center">
  <img src="https://i.imgur.com/xG841Nq.png">
</p>

---

## Endpoints


O desafio conta com uma série de **9 requisitos obrigatórios** e **3 requisitos bônus**. Os endpoints da aplicação podem ser visualizados e executados pela ferramenta de documentação **Swagger** presente na aplicação, no endereço:

```
http://localhost:8080/docs
```

Uma coleção dos endpoints que pode ser importada no **Postman** pode ser encontrada na pasta `docs` dentro de `desafio_spring`, ou <a href="https://github.com/andreepdiasmeli/desafio-spring/blob/master/desafio_spring/docs/Desafio%20Spring%20SocialMeli.postman_collection.json">clicando aqui</a>.

#### Status code retorno

Em casos de exceções causadas por IDs não encontrados no banco optamos por retornar o Status Code `422 (Unprocessable Entity)` ao invés do Status Code `400 (Bad Request)`. O motivo desta alteração é que a sintaxe da requisição está correta, porém a operação não pôde ser concluída devido à lógica de negócio da aplicação, conforme mencionado em <a href="https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Status/422">MDN Web Docs</a>.



## Endpoints extras

Além dos requisitos propostos originalmente, outros endpoints foram criados de modo a extender o propósito da aplicação. **Quatro novos requisitos** foram descritos e as entidades `Category`, `Post`, `Product`, `Seller` e `User` tiveram uma implementação completa de **CRUD's**. Estes novos requisitos podem ser conferidos abaixo.

### 1. Contagem de vendedores que um usuário segue

Este endpoint retorna o número de vendedores que um determinado usuário segue. É bastante similar ao requisito **US-0002**, que retorna o número de seguidores de um vendedor.


**ENDPOINT:** `GET http://localhost:8080/users/{userId}/followed/count`
**Exemplo de RESPONSE BODY:**
```json=
{
    "userId": 6,
    "userName": "Pacheco",
    "followingCount": 2
}
```

### 2. Listagem de publicações de um vendedor

O endpoint criado no **US-0012** foi aprimorado para receber dois parâmetros e permitir a ordenação por data de maneira crescente ou decrescente, e filtrar apenas os produtos promocionais:

**ENDPOINTS:** 

`GET products/{sellerId}/list?order=date_asc`

`GET products/{sellerId}/list?order=date_desc`

`GET products/{sellerId}/list?order=date_desc&onlyPromo=true`

**Exemplo de RESPONSE BODY:** 
```json=
{
    "userId": 4,
    "userName": "Lucas",
    "posts": [
        {
            "postId": 4,
            "date": "2021-07-09",
            "detail": {
                "productId": 4,
                "productName": "XBOX",
                "type": "Gamer",
                "brand": "Microsoft",
                "color": "White",
                "notes": "Much fun",
                "category": {
                    "id": 2,
                    "name": "Video-games"
                }
            },
            "price": 7.85,
            "promotion": {
                "discount": 0.15,
                "expiresAt": "2021-08-09T15:37:46.856464"
            }
        },
        {
            "postId": 6,
            "date": "2021-07-09",
            "detail": {
                "productId": 1,
                "productName": "Macbook",
                "type": "Office",
                "brand": "Apple",
                "color": "Silver",
                "notes": "Very Expensive",
                "category": {
                    "id": 1,
                    "name": "Technology"
                }
            },
            "price": 19999.99,
            "promotion": null
        }
    ]
}
```
