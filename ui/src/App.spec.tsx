import { render, screen } from '@testing-library/react';

import App from './App';

describe('<App />', () => {
  it('should render the App', () => {
    render(<App />);
    const divElement = screen.getByText(/Hello Vite React App!/i);
    (expect(divElement) as any).toBeInTheDocument();
  });
});
