export enum EnumMenuItems {
  PAGINA_INICIAL = "Página Inicial",
  EDITAIS = "Editais",
  ESTATISTICAS = "Estatísticas",
  NOTICIAS = "Noticias",
  EQUIPE = "Equipe",
}

export const MENU_ITEMS = [
  {
    label: EnumMenuItems.PAGINA_INICIAL,
    link: "/portal/home",
    separator: true,
  },
  {
    label: EnumMenuItems.EDITAIS,

    items: [
      {
        label: "Processos seletivos",
        shortcurt: "",
        separator: true,
        subitems: [
          {
            link: "/portal/editais",
            label: "Gerenciar editais",
            shortcurt: "",
          },
          {
            label: "Homologar inscrições",
            shortcurt: "",
          },
          {
            label: "Gerenciar recursos",
            shortcurt: "",
          },
          {
            label: "Resultados preliminares",
            shortcurt: "",
          },
          {
            label: "Resultados finais",
            shortcurt: "",
          },
        ],
      },
      {
        label: "Gerenciar cargos",
        shortcurt: "",
        separator: false,
      },
      {
        label: "Gerenciar vagas",
        shortcurt: "",
        separator: false,
      },
      {
        label: "Gerenciar áreas",
        shortcurt: "",
        separator: false,
      },
    ],
  },
  {
    label: EnumMenuItems.ESTATISTICAS,
    link: "/Estatísticas",
    items: [],
  },
  {
    label: EnumMenuItems.NOTICIAS,
    items: [
      {
        label: "Cadastrar notícia",
        shortcurt: "",
        link: "/portal/noticias",
        subitems: null,
        separator: false,
      },
      {
        label: "Visualizar notícias",
        shortcurt: "",
        subitems: null,
        separator: false,
      },
    ],
  },
  {
    label: EnumMenuItems.EQUIPE,
    // items: [
    //   {
    //     label: "Cadastrar notícia",
    //     shortcurt: "",
    //     subitems: [],
    //     separator: false,
    //   },
    //   {
    //     label: "Visualizar notícias",
    //     shortcurt: "",
    //     subitems: [],
    //     separator: false,
    //   },
    // ],
  },
];
