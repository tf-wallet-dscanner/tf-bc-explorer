import create from 'zustand';

import type { CommonStateCreator } from '.';
import logger from './middlewares/logger';

interface CounterState {
  bears: number;
}

export interface CounterCreateState extends CounterState {
  increasePopulation: () => void;
  removeAllBears: () => void;
}

const initialState: CounterState = {
  bears: 0,
};

const createState: CommonStateCreator<CounterCreateState> = (set) => ({
  ...initialState,
  increasePopulation: () => set((state) => ({ bears: state.bears + 1 })),
  removeAllBears: () => set({ bears: 0 }),
});

const useCounterStore = create(logger(createState, 'counter-store'));

export default useCounterStore;
