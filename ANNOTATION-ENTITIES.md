# Relacionamento @manyToMany, @OneToMany e @ManyToOne 

## Relacionamento muitos-para-muitos entre as entidades Pessoa e Festa

**Classe Pessoa**

```java
package com.apirest.aniversario.entities;

//imports...

@Entity
@Table(name = "pessoas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pessoa_id")
    private Long id;

    private String nome;
    private int idade;

    // Uma pessoa pode ter muitas habilidades
    @OneToMany(mappedBy = "pessoa")
    private Set<Habilidade> habilidades = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinTable(name = "pessoas_festas", 
        joinColumns = @JoinColumn(name = "pessoa_id", referencedColumnName = "idPessoa"), 
        inverseJoinColumns = @JoinColumn(name = "festa_id", referencedColumnName = "festa_id"))
    private Set<Festa> festas = new HashSet<>();
}
```

**Classe Festa**

```java
package com.apirest.aniversario.entities;

//imports...

@Entity
@Table(name = "festas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Festa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFesta")
    private Long id;

    @JsonFormat(pattern = "YYYY-MM-dd")
    private Date data;

    private String localizacao;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "pessoas_festas", 
        joinColumns = @JoinColumn(name = "festa_id", referencedColumnName = "idFesta"), 
        inverseJoinColumns = @JoinColumn(name = "pessoa_id", referencedColumnName = "idPessoa"))
    private Set<Pessoa> pessoas = new HashSet<>();

}
```

### Bom Saber

As anotações `joinColumns` e `inverseJoinColumns` são usadas para mapear as colunas nas tabelas envolvidas em um relacionamento muitos-para-muitos (Many-to-Many) em JPA. Elas determinam como as colunas da tabela atual (entidade atual) estão relacionadas às colunas da tabela alvo (outra entidade) na tabela intermediária que representa o relacionamento.

**Como entender o mapeamento:** 

- `joinColumns`: Isso se refere às colunas da tabela atual (entidade atual) que serão usadas na tabela intermediária para representar a chave estrangeira que faz referência à tabela alvo (outra entidade).

- `inverseJoinColumns`: Isso se refere às colunas da tabela alvo (outra entidade) que serão usadas na tabela intermediária para representar a chave estrangeira que faz referência à tabela atual (entidade atual).

Portanto, `joinColumns` está relacionado à entidade atual (de onde a anotação é definida), e `inverseJoinColumns` está relacionado à outra entidade com a qual você está estabelecendo o relacionamento muitos-para-muitos.

Neste exemplo:

```java
@ManyToMany(cascade = CascadeType.ALL)
@JsonBackReference
@JoinTable(name = "pessoas_festas", 
    joinColumns = @JoinColumn(name = "pessoa_id", referencedColumnName = "idPessoa"), 
    inverseJoinColumns = @JoinColumn(name = "festa_id", referencedColumnName = "idFesta"))
private Set<Festa> festas = new HashSet<>();
```

- `pessoa_id` na tabela intermediária `pessoas_festas` é a chave estrangeira que faz referência à tabela de pessoas.
- `festa_id` na tabela intermediária `pessoas_festas` é a chave estrangeira que faz referência à tabela de festas.
- `id` é a coluna na tabela de pessoas e festas que é usada como chave primária.

Em resumo, `name` se refere ao nome da coluna na tabela intermediária e `referencedColumnName` se refere ao nome da coluna na tabela alvo (outra entidade) que a coluna na tabela intermediária deve referenciar. Isso permite que o JPA saiba como mapear os registros na tabela intermediária com as entidades reais envolvidas no relacionamento muitos-para-muitos.

Ou seja, a propriedade **`name` é a chave estrangeira** e a propriedade **`referencedColumnName` é a chave primária**. Logo, no contexto das anotações joinColumns e inverseJoinColumns, o `name` refere-se ao nome da coluna que será criada na tabela intermediária para representar a chave estrangeira, e o `referencedColumnName` é o nome da coluna na tabela alvo (outra entidade) que será usada como chave primária. Portanto, o `name` é a chave estrangeira e o `referencedColumnName` é a chave primária no relacionamento. É assim que o JPA sabe como mapear os registros na tabela intermediária para as entidades reais envolvidas no relacionamento muitos-para-muitos.

**modelo Relacional**
Pessoa(idPessoa, nome, idade)
Festa(idFesta, data, localizacao)
PessoasFestas(pessoa_id, festa_id)

pessoa_id referencia Pessoa(idPessoa)
festa_id referencia Festa(idFesta)

**Explicação Passo a Passo:**

```java
@ManyToMany(cascade = CascadeType.ALL)
@JsonBackReference
@JoinTable(name = "pessoas_festas", 
    joinColumns = @JoinColumn(name = "pessoa_id", referencedColumnName = "idPessoa"), 
    inverseJoinColumns = @JoinColumn(name = "festa_id", referencedColumnName = "idFesta"))
private Set<Festa> festas = new HashSet<>();
```

- `@ManyToMany(cascade = CascadeType.ALL)`: A anotação `@ManyToMany` é usada para definir um relacionamento muitos-para-muitos entre a entidade atual (que contém esse campo) e a entidade `Festa`. A opção `cascade = CascadeType.ALL` indica que todas as operações (como persistência, remoção, atualização, etc.) realizadas na entidade atual também serão aplicadas à entidade `Festa`.

- `@JsonBackReference`: Essa anotação é usada para resolver problemas de referências cíclicas durante a serialização JSON. Ela é aplicada ao campo `festas` na entidade atual. Isso significa que ao serializar a entidade atual (provavelmente uma entidade chamada `Pessoa`), o campo `festas` não será incluído na saída JSON. Essa anotação evita que ocorra uma serialização infinita ao lidar com relacionamentos bidirecionais.

- `@JoinTable`: A anotação `@JoinTable` é usada para especificar a tabela intermediária que será criada para representar o relacionamento muitos-para-muitos no banco de dados. A tabela intermediária neste caso é chamada de `pessoas_festas`.

- `joinColumns` e `inverseJoinColumns`: Essas propriedades são usadas para mapear as colunas nas tabelas envolvidas no relacionamento. Elas especificam como as colunas da entidade atual (`Pessoa`) estão relacionadas às colunas da entidade alvo (`Festa`). No exemplo, `pessoa_id` é mapeado para `idPessoa`, e `festa_id` é mapeado para `festaId`.

- `private Set<Festa> festas = new HashSet<>();`: Aqui, você está declarando uma coleção `Set` chamada `festas` que representa o lado "muitos" do relacionamento muitos-para-muitos. A coleção será usada para armazenar as festas às quais uma pessoa está associada.

No geral, esse script define um relacionamento muitos-para-muitos entre a entidade atual (provavelmente `Pessoa`) e a entidade `Festa`, e usa a anotação `@JsonBackReference` para evitar problemas de serialização JSON devido a referências cíclicas. A tabela intermediária `pessoas_festas` é usada para representar esse relacionamento no banco de dados.

### Para mais aprendizado

Vejamos esses dois exemplos: 

**Exemplo 1**
```java
package com.apirest.aniversario.entities;

//imports...

public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pessoa_id")
    private Long id;


    @ManyToMany(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinTable(name = "pessoas_festas", 
        joinColumns = @JoinColumn(name = "pessoa_id", referencedColumnName = "idPessoa"), 
        inverseJoinColumns = @JoinColumn(name = "festa_id", referencedColumnName = "idFesta"))
    private Set<Festa> festas = new HashSet<>();

// ... continuação do código

}
```

**Exemplo 2**
```java
package com.apirest.aniversario.entities;

//imports...

public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToMany(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinTable(name = "pessoas_festas", 
        joinColumns = @JoinColumn(name = "pessoa_id"), 
        inverseJoinColumns = @JoinColumn(name = "festa_id"))
    private Set<Festa> festas = new HashSet<>();

// ... continuação do código

}
```

Repare que a propriedade `referencedColumnName` não foi especificado no exemplo 2, isso acontece porque o JPA pode inferir o nome da coluna chave primária da tabela alvo (entidade Pessoa e entidade Festa) automaticamente. O JPA assume que o nome da coluna chave primária na tabela alvo é "id" por padrão, a menos que especifique de outra forma. Logo, o JPA assume O JPA assume que a coluna chave primária na tabela Pessoa e Festa é "id", e, portanto, não é necessário especificar explicitamente `referencedColumnName`.

No entanto, se o nome da coluna chave primária na tabela Pessoa e Festa for diferente de "id", você pode especificar `referencedColumnName` para informar ao JPA qual coluna usar como chave primária. No exemplo 1 podemos renomear a propriedade id com a anotação `@Column`, logo a propriedade `referencedColumnName` é usado para especificar que a coluna chave primária da tabela Pessoa e Festa é "idPessoa" e "idFesta" em vez de "id". Isso é útil quando o nome da coluna chave primária na tabela alvo é diferente do padrão.

## Relacionamento um-para-muitos/muitos-para-um entre as entidades Pessoa e Habilidades

**Classe Pessoa**

```java
package com.apirest.aniversario.entities;

//imports...

@Entity
@Table(name = "pessoas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPessoa")
    private Long id;

    private String nome;
    private int idade;

    // Uma pessoa pode ter muitas habilidades
    @OneToMany(mappedBy = "pessoa")
    private Set<Habilidade> habilidades = new HashSet<>();

    //... continuação do código
}

```

**Classe Habilidade**

```java
package com.apirest.aniversario.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

//imports ...

@Entity
@Table(name = "habilidades")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Habilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHabilidade")
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private Nivel nivel;

    // Muitas habilidades para uma pessoa
    @ManyToOne
    @JoinColumn(name = "pessoaId")
    @JsonBackReference
    private Pessoa pessoa;
    
}
```

**Explicação Passo a Passo:**

Essa é uma relação bidirecional entre duas entidades, `Pessoa` e `Habilidade`, onde uma pessoa pode ter várias habilidades, e cada habilidade pertence a uma pessoa. 

**Na entidade `Pessoa` (lado "um"):**

- `@ManyToOne`: Essa anotação define um relacionamento muitos-para-um com a entidade `Pessoa`. Isso significa que várias habilidades podem estar associadas a uma pessoa (muitas habilidades pertencem a uma pessoa).

- `@JoinColumn(name = "pessoaId")`: Essa anotação especifica a coluna na tabela `Habilidade` que será usada como chave estrangeira para mapear a relação com a entidade `Pessoa`. A coluna "pessoaId" na tabela `Habilidade` será usada para armazenar a referência à pessoa associada a essa habilidade.

- `@JsonBackReference`: Essa anotação é geralmente usada para resolver problemas de referência cíclica ao serializar objetos para JSON. No contexto de relacionamentos bidirecionais, ela evita que ocorra uma referência circular na serialização. Ela instrui o mecanismo JSON (por exemplo, Jackson) a ignorar esse campo ao serializar a entidade `Pessoa` para JSON, impedindo um loop infinito de referências.

**Na entidade `Habilidade` (lado "muitos"):**

- `@OneToMany(mappedBy = "pessoa")`: Essa anotação define um relacionamento um-para-muitos com a entidade `Habilidade`. Isso significa que uma pessoa pode ter várias habilidades (uma pessoa pode ter várias habilidades).

- `mappedBy = "pessoa"`: Isso indica que o mapeamento deste relacionamento é controlado pelo lado oposto, ou seja, o lado "um" (entidade `Pessoa`) controla o mapeamento. O atributo "pessoa" especifica o nome do campo na entidade `Habilidade` que faz referência à entidade `Pessoa`. Neste caso, o campo "pessoa" na entidade `Habilidade` estabelece a ligação entre `Pessoa` e `Habilidade`.

Graças a essa relação bidirecional pode acessar as habilidades de uma pessoa a partir do objeto `Pessoa` e vice-versa, permitindo uma navegação mais flexível entre as entidades em seu aplicativo.