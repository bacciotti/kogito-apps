/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import React from 'react';
import {
  TextContent,
  TextVariants,
  Text,
  Divider,
  TextList,
  TextListItem
} from '@patternfly/react-core';
import { ItemDescriptor } from '@kogito-apps/components-common';
import { OUIAProps, componentOuiaProps } from '@kogito-apps/ouia-tools';

export enum OperationType {
  ABORT = 'ABORT',
  SKIP = 'SKIP',
  RETRY = 'RETRY',
  CANCEL = 'CANCEL'
}

export enum BulkListType {
  PROCESS_INSTANCE = 'process_instance',
  JOB = 'job',
  WORKFLOW = 'workflow'
}
export interface IOperationResult {
  successItems: BulkListItem[];
  failedItems: BulkListItem[];
  ignoredItems: BulkListItem[];
}
export interface IOperationMessages {
  successMessage: string;
  warningMessage?: string;
  ignoredMessage: string;
  noItemsMessage: string;
}

export interface IOperationFunctions {
  perform: () => void;
}

export interface IOperationResults {
  [key: string]: IOperationResult;
}

export interface IOperation {
  type: string;
  results: IOperationResult;
  messages: IOperationMessages;
  functions: IOperationFunctions;
}

export interface IOperations {
  [key: string]: IOperation;
}
export interface IOwnProps {
  operationResult: IOperation;
}
export interface BulkListItem {
  id: string;
  name: string;
  description: string;
  errorMessage?: string;
}

const BulkList: React.FC<IOwnProps & OUIAProps> = ({
  operationResult,
  ouiaId,
  ouiaSafe
}) => {
  const iterateItems = (itemList: BulkListItem[]) => {
    return (
      <TextList>
        {itemList.map((item: BulkListItem) => {
          return (
            <TextListItem key={item.id}>
              <strong>
                <ItemDescriptor itemDescription={item} />
              </strong>{' '}
              {item.errorMessage && <span> - {item.errorMessage}</span>}
            </TextListItem>
          );
        })}
      </TextList>
    );
  };
  return (
    <div {...componentOuiaProps(ouiaId, 'bulk-list', ouiaSafe)}>
      {operationResult.results.successItems.length > 0 ? (
        <>
          <TextContent>
            <Text component={TextVariants.h2}>
              {operationResult.messages.successMessage}
            </Text>
            {iterateItems(operationResult.results.successItems)}
          </TextContent>
          {operationResult.results.successItems.length !== 0 &&
            operationResult.messages.warningMessage && (
              <TextContent className="pf-u-mt-sm">
                <Text component={TextVariants.small}>
                  {operationResult.messages.warningMessage}
                </Text>
              </TextContent>
            )}
        </>
      ) : (
        <TextContent>
          <Text component={TextVariants.h2}>
            {operationResult.messages.noItemsMessage}
          </Text>
        </TextContent>
      )}
      {operationResult.results.ignoredItems.length !== 0 && (
        <>
          <Divider component="div" className="pf-u-my-xl" />
          <TextContent>
            <Text component={TextVariants.h2}>
              {operationResult.type === BulkListType.PROCESS_INSTANCE && (
                <span>Ignored process instances:</span>
              )}
              {operationResult.type === BulkListType.WORKFLOW && (
                <span>Ignored workflows:</span>
              )}
              {operationResult.type === BulkListType.JOB && (
                <span>Ignored jobs:</span>
              )}
            </Text>
            <Text component={TextVariants.small} className="pf-u-mt-sm">
              <span>{operationResult.messages.ignoredMessage}</span>
            </Text>
            {iterateItems(operationResult.results.ignoredItems)}
          </TextContent>
        </>
      )}
      {operationResult.results.failedItems.length !== 0 && (
        <>
          <Divider component="div" className="pf-u-my-xl" />
          <TextContent>
            <Text component={TextVariants.h2}>Errors:</Text>
            {iterateItems(operationResult.results.failedItems)}
          </TextContent>
        </>
      )}
    </div>
  );
};

export default BulkList;
