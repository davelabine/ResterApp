import { StudentData } from '../types';

export const STUDENT_DATA_EMPTY: StudentData = new StudentData();

export const STUDENT_DATA_BILLY: StudentData =  {
                                                    'skey': '8a80810d5dd89a3f015dd89ac8280000',
                                                    'id': 'Billy Bob',
                                                    'name': '1234',
                                                    'photo': {
                                                        'bucketName': 'resterapp-dev',
                                                        'key': 'e93ecbf0-ba96-4290-a906-5ea67e4c4a9f'
                                                    }
                                                };

export const LIST_STUDENT_DATA_EMPTY: StudentData[] = [];

export const LIST_STUDENT_DATA_TWO: StudentData[] =  [
    {
        'skey': '8a80810d5dd89a3f015dd89ac8280000',
        'id': 'Billy Bob',
        'name': '1234',
        'photo': {
            'bucketName': 'resterapp-dev',
            'key': 'e93ecbf0-ba96-4290-a906-5ea67e4c4a9f'
        }
    },
    {
        'skey': '8a80810d5dd89a3f015dd8a252800001',
        'id': '503074',
        'name': 'Albert Russell',
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
        'name': 'Billy Bob',
        'photo': {
            'bucketName': 'resterapp-dev',
            'key': 'e93ecbf0-ba96-4290-a906-5ea67e4c4a9f'
        }
    },
    {
        'skey': '8a80810d5dd89a3f015dd8a252800001',
        'id': '503074',
        'name': 'Albert Russell',
        'photo': {
            'bucketName': 'resterapp-dev',
            'key': 'e93ecbf0-ba96-4290-a906-5ea67e4c4a9f'
        }
    },
    {
        'skey': '8a80810d5dd89a3f015dd8a259b2000e',
        'id': '942226',
        'name': 'Alfredo Cummings',
        'photo': {}
    },
    {
        'skey': '8a80810d5dd89a3f015dd8a2677f0027',
        'id': '824388',
        'name': 'Allen Russell',
        'photo': {}
    },
    {
        'skey': '8a80810d5dd89a3f015dd8a271fb003a',
        'id': '266192',
        'name': 'Andre McKenna',
        'photo': {}
    },
    {
        'skey': '8a80810d5dd89a3f015dd8a27f0d0052',
        'id': '932083',
        'name': 'Angelica Obrien',
        'photo': {}
    },
    {
        'skey': '8a80810d5dd89a3f015dd8a281c80057',
        'id': '682307',
        'name': 'Anna Washington',
        'photo': {}
    },
    {
        'skey': '8a80810d5dd89a3f015dd8a27c52004d',
        'id': '734552',
        'name': 'Antonio Fowler',
        'photo': {}
    },
    {
        'skey': '8a80810d5dd89a3f015dd8a25fb80019',
        'id': '615643',
        'name': 'Ashley Mcdaniel',
        'photo': {}
    },
    {
        'skey': '8a80810d5dd89a3f015dd8a25897000c',
        'id': '753238',
        'name': 'Beatrice Russell',
        'photo': {}
    },
];
