import React, { useEffect, useState } from 'react';
import { Dimensions, TouchableOpacity, View } from 'react-native';
import { PageContainer } from '../../../components/ui/layout/PageContainer';
import {
  ContentContainer,
  ScrollContentContainer,
} from '../../../components/ui/layout/ContentContainer.tsx';
import { Color } from '../../../constants/color.constant.ts';
import {
  BodyTextB,
  BodyTextM,
  CaptionB,
  Title,
} from '../../../components/ui/base/TextBase';
import { formatDateToTodayOrYYMMDD } from '../../../utils/date-formatter.util.ts';
import { AdaptiveImage } from '../../../components/ui/base/ImageBase';
import { useAiGeneratedVideos } from '../../../services/gallery/gallery.query';
import {
  AiGeneratedVideo,
  VideoGenerationStatus,
} from '../../../types/external/ai-photo.type';

interface WorkItem {
  id: number;
  galleryId: number;
  drivingVideoId: number;
  status: VideoGenerationStatus;
  startedAt?: string;
  completedAt?: string;
  videoUrl?: string;
  createdAt: string;
}

const AiPhotoWorkHistoryPage = (): React.ReactElement => {
  // React hooks
  const [inProgressItems, setInProgressItems] = useState<AiGeneratedVideo[]>(
    [],
  );
  const [completedItems, setCompletedItems] = useState<AiGeneratedVideo[]>([]);

  // Custom hooks
  const { generatedVideos } = useAiGeneratedVideos();

  // Derived value or local variables
  const screenWidth = Dimensions.get('window').width;

  // Custom functions
  const renderWorkItem = (item: WorkItem) => {
    const itemWidth = (screenWidth - 48) / 2; // 좌우 패딩 20px씩, 아이템 간격 20px
    const isInProgress =
      item.status === 'PENDING' || item.status === 'IN_PROGRESS';

    return (
      <TouchableOpacity
        key={item.id}
        style={{
          width: itemWidth,
          marginBottom: 20,
        }}
      >
        <ContentContainer gap={8}>
          <ContentContainer
            flex={1}
            backgroundColor={Color.GREY_700}
            borderRadius={6}
            height={itemWidth * 0.85}
          >
            {item.videoUrl ? (
              <AdaptiveImage uri={item.videoUrl} style={{ flex: 1 }} />
            ) : (
              <View style={{ flex: 1, backgroundColor: Color.GREY_600 }} />
            )}
          </ContentContainer>
          {isInProgress ? (
            <ContentContainer
              useHorizontalLayout
              gap={6}
              justifyContent={'flex-start'}
            >
              <BodyTextM color={Color.GREY_700}>
                {formatDateToTodayOrYYMMDD(item.startedAt ?? item.createdAt)}{' '}
              </BodyTextM>
              <BodyTextB color={Color.AI_500}>생성중...</BodyTextB>
            </ContentContainer>
          ) : item.status === 'FAILED' ? (
            <ContentContainer
              useHorizontalLayout
              gap={6}
              justifyContent={'flex-start'}
            >
              <BodyTextM color={Color.GREY_700}>
                {formatDateToTodayOrYYMMDD(item.createdAt)}{' '}
              </BodyTextM>
              <BodyTextB color={Color.ERROR_300}>생성 실패</BodyTextB>
            </ContentContainer>
          ) : (
            <ContentContainer gap={0}>
              <CaptionB color={Color.GREY_300}>
                {formatDateToTodayOrYYMMDD(item.startedAt ?? item.createdAt)}{' '}
                요청
              </CaptionB>
              {item.completedAt && (
                <CaptionB color={Color.GREY_300}>
                  {formatDateToTodayOrYYMMDD(item.completedAt)} 완료
                </CaptionB>
              )}
            </ContentContainer>
          )}
        </ContentContainer>
      </TouchableOpacity>
    );
  };

  // Side effects
  useEffect(() => {
    if (generatedVideos && generatedVideos.length > 0) {
      setInProgressItems(
        generatedVideos.filter(
          item => item.status === 'PENDING' || item.status === 'IN_PROGRESS',
        ),
      );
      setCompletedItems(
        generatedVideos.filter(
          item => item.status === 'COMPLETED' || item.status === 'FAILED',
        ),
      );
    }
  }, [generatedVideos]);

  return (
    <PageContainer edges={['left', 'right', 'bottom']}>
      <ScrollContentContainer>
        <ContentContainer withContentPadding paddingVertical={24} gap={32}>
          <ContentContainer paddingTop={8}>
            <BodyTextM color={Color.GREY_500}>
              일반적으로 약 <BodyTextM color={Color.AI_500}>2</BodyTextM>분 정도
              소요되며,{'\n'}
              다른 화면으로 이동해도 문제없이 완료돼요.
            </BodyTextM>
          </ContentContainer>

          {inProgressItems.length > 0 && (
            <ContentContainer gap={12}>
              <Title color={Color.GREY_900}>진행중인 내역</Title>
              <View
                style={{
                  flexDirection: 'row',
                  flexWrap: 'wrap',
                  justifyContent: 'space-between',
                }}
              >
                {inProgressItems.map(renderWorkItem)}
              </View>
            </ContentContainer>
          )}
          <ContentContainer gap={12}>
            <Title color={Color.GREY_900}>완료된 내역</Title>
            <View
              style={{
                flexDirection: 'row',
                flexWrap: 'wrap',
                justifyContent: 'space-between',
              }}
            >
              {completedItems.map(renderWorkItem)}
            </View>
          </ContentContainer>
        </ContentContainer>
      </ScrollContentContainer>
    </PageContainer>
  );
};

export { AiPhotoWorkHistoryPage };
