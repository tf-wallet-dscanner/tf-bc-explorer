import Counter from 'components/counter';
import globalStyles from 'styles/global';
import { apply, theme, tw } from 'twind';
import { css } from 'twind/css';

const wrapper = css`
  background-color: ${theme('backgroundColor.tooltip')};
  &:hover {
    ${apply`bg-red-500`}
  }
`;

const wrapper12121 = css`
  ${apply`text-red-500`}
`;

const btn = apply`inline-block bg-red-600 text-red-100`;

function App() {
  return (
    <div className={tw(globalStyles)}>
      <div className={tw(wrapper)}>
        <p className={tw(wrapper12121)}>Hello Vite React App!</p>
        <Counter />
        <button className={tw(btn)}>click!</button>
      </div>
    </div>
  );
}

export default App;
