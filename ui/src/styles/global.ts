import { apply, css, theme } from 'twind/css';

const globalStyles = css({
  ':global': {
    p: {
      color: theme('colors.blue.500'),
      '&:hover': apply`text-gray-800`,
    },
  },
});

export default globalStyles;
