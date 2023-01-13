import { createGlobalStyle } from "styled-components";

const GlobalStyle = createGlobalStyle`
html, body, div, span, applet, object, iframe,
h1, h2, h3, h4, h5, h6, p, blockquote, pre,
a, abbr, acronym, address, big, cite, code,
del, dfn, em, img, ins, kbd, q, s, samp,
small, strike, strong, sub, sup, tt, var,
b, u, i, center,
dl, dt, dd, ol, ul, li,
fieldset, form, label, legend,
table, caption, tbody, tfoot, thead, tr, th, td,
article, aside, canvas, details, embed,
figure, figcaption, footer, header, hgroup,
menu, nav, output, ruby, section, summary,
time, mark, audio, video {
margin: 0;
padding: 0;
border: 0;
font-size: 100%;
font: inherit;
vertical-align: baseline;
font-family: 'Spoqa Han Sans Neo', 'sans-serif';
}
/ HTML5 display-role reset for older browsers /
article, aside, details, figcaption, figure,
footer, header, hgroup, menu, nav, section {
display: block;
}
body {
line-height: 1;
font-family: 'Spoqa Han Sans Neo', 'sans-serif';
}
ol, ul {
list-style: none;
}
blockquote, q {
quotes: none;
}
blockquote:before, blockquote:after,
q:before, q:after {
content: '';
content: none;
}
table {
border-collapse: collapse;
border-spacing: 0;
}
html {
    --color-text: black;
    --color-background: white;
    --color-primary:#ffbf00;
    --color-gray:#747272;
    --color-sidebar:#F9F6F4;
    --color-input-focus:#fcb500;
    --color-account-naver:#32D25D;
    --color-account-apple:#444543;
    --color-course-intro:#fdfdfd;
    --color-red:#FF5454;
    --color-box-gray:#d0cfcf;
    --color-box-primary:#ffd555;


    --weight-point:900;
    --weight-middle:500;
    --weight-thin:300;

    --size-login-btn:1.1rem;
    --size-rank-title:26px;
    --size-card-title:16px;
    --size-card-any:11px;
    --size-footer:14px;
    --size-border-radius:10px;
    --size-mypage-title:30px;
    --size-own-nav:18px;
    --size-revise-coupon-window:70%;
    --size-category-title: 35px;
    --size-category-big:26px;
    --size-category-small:23px;
  }
  button{
    cursor: pointer;
    font-family: 'Spoqa Han Sans Neo', 'sans-serif';
  }
  a{
    cursor: pointer;
    font-family: 'Spoqa Han Sans Neo', 'sans-serif';
  }
`;

export default GlobalStyle;
