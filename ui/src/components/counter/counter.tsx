import { useEffect } from 'react';
import { useCounterStore } from 'store';
import { tw } from 'twind';

function Counter() {
  const { bears, increasePopulation } = useCounterStore((state) => ({
    bears: state.bears,
    increasePopulation: state.increasePopulation,
  }));

  useEffect(() => {
    const timer = setTimeout(() => increasePopulation(), 1000);
    return () => clearTimeout(timer);
  }, [bears, increasePopulation]);

  return (
    <div className={tw`container w-1/2`}>
      <p>
        Page has been open for <code>{bears}</code> seconds.
      </p>
    </div>
  );
}

export default Counter;
