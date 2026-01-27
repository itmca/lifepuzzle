import { HeroType, HeroUserType } from '../../types/core/hero.type';
import { useAuthQuery } from '../core/auth-query.hook';
import { queryKeys } from '../core/query-keys';
import { extractHeroesFromQueryResponse } from './hero-transformer.util';

export type UseHeroReturn = {
  hero: HeroType | undefined;
  isLoading: boolean;
  isError: boolean;
  refetch: () => void;
};

export type HeroesQueryResponse = {
  heroes: HeroQueryResponse[];
};

export type HeroQueryResponse = {
  hero: HeroType;
  puzzleCnt: number;
  users: HeroUserType[];
};

export const useHero = (heroNo: number): UseHeroReturn => {
  const query = useAuthQuery<HeroQueryResponse>({
    queryKey: queryKeys.hero.detail(heroNo),
    axiosConfig: {
      url: `/v1/heroes/${heroNo}`,
      method: 'get',
    },
    enabled: heroNo >= 0,
  });

  return {
    hero: query.data?.hero,
    isLoading: query.isFetching,
    isError: query.isError,
    refetch: query.refetch,
  };
};

export type UseHeroesReturn = {
  heroes: HeroType[];
  isLoading: boolean;
  isError: boolean;
  refetch: () => void;
};

export const useHeroes = (): UseHeroesReturn => {
  const query = useAuthQuery<HeroesQueryResponse>({
    queryKey: queryKeys.hero.all,
    axiosConfig: {
      url: '/v1/heroes',
    },
  });

  const heroes = query.data?.heroes
    ? extractHeroesFromQueryResponse(query.data.heroes)
    : [];

  return {
    heroes,
    isLoading: query.isFetching,
    isError: query.isError,
    refetch: query.refetch,
  };
};
