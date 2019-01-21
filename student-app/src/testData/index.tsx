import { StudentData } from '../types';

export const STUDENT_DATA_EMPTY: StudentData = new StudentData();

export const STUDENT_DATA_FAKE: StudentData =  {
    'skey': 'FAKE_KEY',
    'id': 'FAKE_ID',
    'lastName': 'FAKE_NAME',
    'photo': {}
};

export const STUDENT_DATA_BILLY: StudentData =  {
                                                    'skey': '8a80810d5dd89a3f015dd89ac8280000',
                                                    'id': '1234',
                                                    'lastName': 'Billy Bob',
                                                    'photo': {
                                                        'bucketName': 'resterapp-dev',
                                                        'key': 'e93ecbf0-ba96-4290-a906-5ea67e4c4a9f'
                                                    }
                                                };

export const STUDENT_DATA_BILLY_UPDATE: StudentData =  {
                                                    'skey': '8a80810d5dd89a3f015dd89ac8280000',
                                                    'id': '007',
                                                    'lastName': 'James Bond',
                                                    'photo': {}
                                                };

export const LIST_STUDENT_DATA_EMPTY: StudentData[] = [];

export const LIST_STUDENT_DATA_TWO: StudentData[] =  [
    {
        'skey': '8a80810d5dd89a3f015dd89ac8280000',
        'id': '1234',
        'lastName': 'Billy Bob',
        'photo': {
            'bucketName': 'resterapp-dev',
            'key': 'e93ecbf0-ba96-4290-a906-5ea67e4c4a9f'
        }
    },
    {
        'skey': '8a80810d5dd89a3f015dd8a252800001',
        'id': '503074',
        'lastName': 'Albert Russell',
        'photo': {
            'bucketName': 'resterapp-dev',
            'key': 'e93ecbf0-ba96-4290-a906-5ea67e4c4a9f'
        }
    },
];

export const LIST_STUDENT_DATA_BIG: StudentData[] =  [
    {
        'skey': '8a80810d5dd89a3f015dd89ac8280000',
        'id': '1234',
        'lastName': 'Billy Bob',
        'photo': {
            'bucketName': 'resterapp-dev',
            'key': 'e93ecbf0-ba96-4290-a906-5ea67e4c4a9f'
        }
    },
    {
        'skey': '8a80810d5dd89a3f015dd8a252800001',
        'id': '503074',
        'lastName': 'Albert Russell',
        'photo': {
            'bucketName': 'resterapp-dev',
            'key': 'e93ecbf0-ba96-4290-a906-5ea67e4c4a9f'
        }
    },
    {
        'skey': '8a80810d5dd89a3f015dd8a259b2000e',
        'id': '942226',
        'lastName': 'Alfredo Cummings',
        'photo': {}
    },
    {
        'skey': '8a80810d5dd89a3f015dd8a2677f0027',
        'id': '824388',
        'lastName': 'Allen Russell',
        'photo': {}
    },
    {
        'skey': '8a80810d5dd89a3f015dd8a271fb003a',
        'id': '266192',
        'lastName': 'Andre McKenna',
        'photo': {}
    },
    {
        'skey': '8a80810d5dd89a3f015dd8a27f0d0052',
        'id': '932083',
        'lastName': 'Angelica Obrien',
        'photo': {}
    },
    {
        'skey': '8a80810d5dd89a3f015dd8a281c80057',
        'id': '682307',
        'lastName': 'Anna Washington',
        'photo': {}
    },
    {
        'skey': '8a80810d5dd89a3f015dd8a27c52004d',
        'id': '734552',
        'lastName': 'Antonio Fowler',
        'photo': {}
    },
    {
        'skey': '8a80810d5dd89a3f015dd8a25fb80019',
        'id': '615643',
        'lastName': 'Ashley Mcdaniel',
        'photo': {}
    },
    {
        'skey': '8a80810d5dd89a3f015dd8a25897000c',
        'id': '753238',
        'lastName': 'Beatrice Russell',
        'photo': {}
    },
];
